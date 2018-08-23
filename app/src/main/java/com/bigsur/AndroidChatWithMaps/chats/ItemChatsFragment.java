package com.bigsur.AndroidChatWithMaps.chats;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.DBManager.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.Create;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.Delete;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.GetAll;
import com.bigsur.AndroidChatWithMaps.DBManager.crud.Update;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class ItemChatsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    ListView lvMain;
    Toolbar toolbar;

    public static ItemChatsFragment newInstance() {
        ItemChatsFragment fragment = new ItemChatsFragment();
        Log.d(TAG, "newInstance: ItemChatsFragment ");
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);
        findViewsById(view);
        refreshDialogList();

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialog = li.inflate(R.layout.create_dialog, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                mDialogBuilder.setView(dialog);
                final EditText alterDialogName = (EditText) dialog.findViewById(R.id.contactName);
                final EditText alterDialogPhoneNumber = (EditText) dialog.findViewById(R.id.contactPhoneNumber);
                String selectedFromList = (lvMain.getItemAtPosition(position).toString());
                alterDialogName.setText(selectedFromList);
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Update db = new Update();
                                        db.execute();//1111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                                        refreshDialogList();
                                    }
                                })
                        .setNegativeButton("delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Delete db = new Delete();
                                        //(Contacts)lvMain.getItemAtPosition(position) дает только строку с именем. потому что именно ее
                                        //мы туда и засовывали.
                                        //поэтому нужно поменять arrayAdapter на cursorAdapter
                                        //чтобы брать отображать в листвью именно поля объектов,
                                        //а не массив отдельных каких-то херовин с именами
                                        db.execute();//1111111111111111111111111111111111111111111111111111111111111111111111111111111111111
                                        refreshDialogList();
                                    }
                                });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();

                final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                LinearLayout.LayoutParams buttonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                buttonLL.weight = 1;
                buttonLL.gravity = Gravity.CENTER;
                positiveButton.setLayoutParams(buttonLL);
                negativeButton.setLayoutParams(buttonLL);




                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_or_delete_chat_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addDialog:
                onClickAddDialogButton();
                return true;

            case R.id.addGroupDialog:
                // do smth
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void findViewsById(View view) {
        lvMain = (ListView) view.findViewById(R.id.lvMain);
        toolbar = (Toolbar) view.findViewById(R.id.chat_toolbar);
    }


    private ArrayList<String> showContactsNames(List<Contacts> contactList) {
        ArrayList<String> contactsNames = new ArrayList<>();

        for (Contacts contact : contactList) {
            contactsNames.add(contact.getName());
        }
        return contactsNames;
    }

    private List<Contacts> getContacts() {
        GetAll db = new GetAll();
        db.execute();
        List<Contacts> listViewContacts = new ArrayList<>();
        try {
            if(db.get() == null) {
                listViewContacts.add(0, new Contacts("default", "1"));
            } else {
                listViewContacts = db.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
       return listViewContacts;
    }

    private Adapter refreshDialogList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.contact_list, showContactsNames(getContacts()));
        lvMain.setAdapter(adapter);
        return adapter;
    }


    public void onClickAddDialogButton() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialog = li.inflate(R.layout.create_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
        mDialogBuilder.setView(dialog);
        final EditText alterDialogName = (EditText) dialog.findViewById(R.id.contactName);
        final EditText alterDialogPhoneNumber = (EditText) dialog.findViewById(R.id.contactPhoneNumber);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("create dialog",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Create db = new Create();
                                db.execute(new Contacts(alterDialogName.getText().toString(), alterDialogPhoneNumber.getText().toString()));
                                refreshDialogList();
                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();

        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams buttonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        buttonLL.weight = 1;
        buttonLL.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(buttonLL);
        negativeButton.setLayoutParams(buttonLL);
    }
}




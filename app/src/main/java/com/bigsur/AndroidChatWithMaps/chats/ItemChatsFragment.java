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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.DBManager.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.concurrent.ExecutionException;


public class ItemChatsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    ListView lvMain;
    Toolbar toolbar;
    ArrayAdapter<ChatRooms> adapter;
    SQLiteContactsManager dbStorage = new SQLiteContactsManager();



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
        adapter = new ArrayAdapter<ChatRooms>(getContext(), R.layout.chats_list);
        lvMain.setAdapter(adapter);


        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialog = li.inflate(R.layout.create_dialog, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                mDialogBuilder.setView(dialog);
                final EditText alterDialogName = (EditText) dialog.findViewById(R.id.contactName);
                final EditText alterDialogPhoneNumber = (EditText) dialog.findViewById(R.id.contactPhoneNumber);
                alterDialogName.setText(adapter.getItem(position).getName());
               // alterDialogPhoneNumber.setText(adapter.getItem(position).getPhone_number());
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        adapter.getItem(position).setName(alterDialogName.getText().toString());
                                     //   adapter.getItem(position).setPhone_number(alterDialogPhoneNumber.getText().toString());
                                    //    dbStorage.update(adapter.getItem(position));
                                        try {
                                            refreshDialogList();
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                        .setNegativeButton("delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dbStorage.delete(adapter.getItem(position).getId());
                                        try {
                                            refreshDialogList();
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
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


    private void refreshDialogList() throws ExecutionException, InterruptedException {
        adapter = new ArrayAdapter<ChatRooms>(getContext(), R.layout.chats_list);
        lvMain.setAdapter(adapter);
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
                                DataFromDB<Contacts> contact = new DataFromDB<>(new Contacts(alterDialogName.getText().toString(), alterDialogPhoneNumber.getText().toString()));
                                dbStorage.create(contact);
                                Log.d(TAG, "onClick: "+ dbStorage.toString());
                                try {
                                    refreshDialogList();
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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




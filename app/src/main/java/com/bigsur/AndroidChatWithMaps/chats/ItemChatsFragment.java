package com.bigsur.AndroidChatWithMaps.chats;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.bigsur.AndroidChatWithMaps.DBManager.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.R;


public class ItemChatsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    EditText dialogName;
    EditText dialogPhoneNumber;
    ListView lvMain;
    Toolbar toolbar;
    String[] contacts = new String[]{"mavr0", "mavr1", "mavr2", "mavr3", "mavr4", "mavr5", "mavr6", "mavr7", "mavr8", "mavr9", "mavr10", "mavr11", "mavr12", "mavr13", "mavr14", "mavr15"};

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, contacts);

        lvMain.setAdapter(adapter);
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

            case R.id.deleteDialog:
                // do smth
                return true;

            case R.id.deleteGroupDialog:
                // do smth
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void findViewsById(View view) {
        dialogName = (EditText) view.findViewById(R.id.contactName);
        dialogPhoneNumber = (EditText) view.findViewById(R.id.contactPhoneNumber);
        lvMain = (ListView) view.findViewById(R.id.lvMain);
        toolbar = (Toolbar) view.findViewById(R.id.chat_toolbar);
    }

    public void onClickAddDialogButton() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.create_dialog, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
        mDialogBuilder.setView(promptsView);
        final EditText dialogName = (EditText) promptsView.findViewById(R.id.contactName);
        final EditText dialogPhoneNumber = (EditText) promptsView.findViewById(R.id.contactName);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("create dialog",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Thread(new Runnable() {
                                    public void run() {
                                        AppDatabase db = App.getInstance().getDatabase();
                                        ContactsDAO contactsDao = db.getContactsDao();
                                        Contacts contacts = new Contacts(dialogName.getText().toString(), dialogPhoneNumber.getText().toString());
                                        contactsDao.insert(contacts);
                                        Log.d(TAG, String.valueOf(contactsDao.getAll()));
                                    }
                                }).start();
                            }
                        });

                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();


    }
}




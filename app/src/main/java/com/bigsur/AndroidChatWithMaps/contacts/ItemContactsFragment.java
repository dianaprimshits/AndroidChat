package com.bigsur.AndroidChatWithMaps.contacts;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.DBManager.Adapters.CustomAdapterForContacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.concurrent.ExecutionException;


public class ItemContactsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    TextView contactsNumberTV;
    ListView lvMain;
    Toolbar toolbar;
    CustomAdapterForContacts adapter;
    SQLiteContactsManager dbStorage = new SQLiteContactsManager();



    public static ItemContactsFragment newInstance() {
        ItemContactsFragment fragment = new ItemContactsFragment();
        Log.d(TAG, "newInstance: ItemContactsFragment.");
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
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        findViewsById(view);


        try {
            String contactsTVText;
            int contactsNumber = dbStorage.getContactsNumber();
            int contactsNumberLastDigit = contactsNumber % 10;
            if (contactsNumberLastDigit == 1) {
                contactsTVText = String.format("%d contact", contactsNumber);
            } else {
                contactsTVText = String.format("%d contacts", contactsNumber);
            }

            contactsNumberTV.setText(contactsTVText);
            adapter = new CustomAdapterForContacts(getContext(), dbStorage.getAll());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        lvMain.setAdapter(adapter);


        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialog = li.inflate(R.layout.create_contact, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                mDialogBuilder.setView(dialog);
                final EditText alterDialogName = (EditText) dialog.findViewById(R.id.contactName);
                final EditText alterDialogPhoneNumber = (EditText) dialog.findViewById(R.id.contactPhoneNumber);
                Contacts selectedItem = (Contacts) adapter.getItem(position).getData();
                alterDialogName.setText(selectedItem.getContactName());
                alterDialogPhoneNumber.setText(selectedItem.getPhone_number());
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("update",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Contacts selectedItem = (Contacts) adapter.getItem(position).getData();
                                        selectedItem.setContactName(alterDialogName.getText().toString());
                                        selectedItem.setPhone_number(alterDialogPhoneNumber.getText().toString());
                                        dbStorage.update(adapter.getItem(position));
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
                                        Contacts selectedItem = (Contacts) adapter.getItem(position).getData();
                                        dbStorage.delete(selectedItem.getId());
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
        inflater.inflate(R.menu.create_contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onClickAddDialogButton();
        return true;
    }


    private void findViewsById(View view) {
        lvMain = (ListView) view.findViewById(R.id.lvMain);
        toolbar = (Toolbar) view.findViewById(R.id.chat_toolbar);
        contactsNumberTV = (TextView) view.findViewById(R.id.contactsNumberTV);
    }


    private void refreshDialogList() throws ExecutionException, InterruptedException {
        adapter = new CustomAdapterForContacts(getContext(), dbStorage.getAll());
        lvMain.setAdapter(adapter);
    }


    public void onClickAddDialogButton() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialog = li.inflate(R.layout.create_contact, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
        mDialogBuilder.setView(dialog);
        final EditText alterDialogName = (EditText) dialog.findViewById(R.id.contactName);
        final EditText alterDialogPhoneNumber = (EditText) dialog.findViewById(R.id.contactPhoneNumber);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("create contact",
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


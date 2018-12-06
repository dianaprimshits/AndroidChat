package com.bigsur.AndroidChatWithMaps.UI.Contacts;


import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContact;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview;


public class ItemContactsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    TextView contactsNumberTV;
    Toolbar toolbar;
    DataWithIconListview lvMain;
    DataWithIconManager dbStorage = ViewableContactManager.getInstance();


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

        String contactsTVText;
        int contactsNumber = dbStorage.getAll().size();
        int contactsNumberLastDigit = contactsNumber % 10;
        if (contactsNumberLastDigit == 1) {
            contactsTVText = String.format("%d contact", contactsNumber);
        } else {
            contactsTVText = String.format("%d contacts", contactsNumber);
        }

        contactsNumberTV.setText(contactsTVText);
        lvMain.init(dbStorage, new CustomContactsAdapter(getContext(), dbStorage), "contacts", getActivity());


        dbStorage.addDataChangeListener(this, new Runnable() {
            @Override
            public void run() {
                String contactsTVText;
                int contactsNumber = dbStorage.getAll().size();
                int contactsNumberLastDigit = contactsNumber % 10;
                if (contactsNumberLastDigit == 1) {
                    contactsTVText = String.format("%d contact", contactsNumber);
                } else {
                    contactsTVText = String.format("%d contacts", contactsNumber);
                }
                contactsNumberTV.setText(contactsTVText);
            }
        });


        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
              /*  DataModifier dataModifier = new ContactsModifier(getContext());
                dataModifier.init(getContext(), new CustomContactsAdapter(getContext(), dbStorage), position);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());

                mDialogBuilder.setView(dataModifier.getView());
                AlertDialog alertDialog = mDialogBuilder.create();
                alertDialog.show();*/
                return true;
            }
        });
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.create_contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.top_contact_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.contactsAdd:
                onClickAddDialogButton();
                return true;
            case R.id.contact_fr_search:
                Intent goSearchIntent = new Intent(getActivity(), ContactsSearchActivity.class);
                startActivity(goSearchIntent);
                return true;
        }
        return true;
    }


    private void findViewsById(View view) {
        lvMain = (DataWithIconListview) view.findViewById(R.id.lvMain);
        toolbar = (Toolbar) view.findViewById(R.id.chat_toolbar);
        toolbar = (Toolbar) view.findViewById(R.id.contacts_toolbar);
        contactsNumberTV = (TextView) view.findViewById(R.id.contactsNumberTV);
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
                                DataWithIcon contact = new ViewableContact(alterDialogName.getText().toString(), alterDialogPhoneNumber.getText().toString());
                                dbStorage.create(contact);
                                Log.d(TAG, "onClick: " + dbStorage.toString());

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


package com.bigsur.AndroidChatWithMaps.UI.Contacts;


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

import com.bigsur.AndroidChatWithMaps.ContactsDataManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContact;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataModifierView.ContactsModifier;
import com.bigsur.AndroidChatWithMaps.UI.DataModifierView.DataModifier;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;

import java.util.concurrent.ExecutionException;


public class ItemContactsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    TextView contactsNumberTV;
    Toolbar toolbar;
    DataWithIconListview lvMain;
    ContactsDataManager dbStorage = new ContactsDataManager();

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

        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        dbStorage.addDataChangeListener(this, () -> {
            String contactsTVText1;
            int contactsNumber1 = dbStorage.getAll().size();
            int contactsNumberLastDigit1 = contactsNumber1 % 10;
            if (contactsNumberLastDigit1 == 1) {
                contactsTVText1 = String.format("%d contact", contactsNumber1);
            } else {
                contactsTVText1 = String.format("%d contacts", contactsNumber1);
            }
            contactsNumberTV.setText(contactsTVText1);
        });


        lvMain.setOnItemLongClickListener((parent, view1, position, id) -> {
            DataModifier dataModifier = new ContactsModifier(getContext());
            dataModifier.init(getContext(), new CustomContactsAdapter(getContext(), dbStorage), position, getActivity());
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = mDialogBuilder.setView(dataModifier.getView()).create();
            alertDialog.show();
            return true;
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
        lvMain = view.findViewById(R.id.lvMain);
        toolbar = view.findViewById(R.id.chat_toolbar);
        toolbar = view.findViewById(R.id.contacts_toolbar);
        contactsNumberTV = view.findViewById(R.id.contactsNumberTV);
    }


    public void onClickAddDialogButton() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialog = li.inflate(R.layout.create_contact, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
        mDialogBuilder.setView(dialog);
        final EditText alterDialogName = dialog.findViewById(R.id.contactName);
        final EditText alterDialogPhoneNumber = dialog.findViewById(R.id.contactPhoneNumber);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("create contact",
                        (dialog12, id) -> {
                            DataWithIcon contact = new ViewableContact(alterDialogName.getText().toString(), alterDialogPhoneNumber.getText().toString());
                            try {
                                dbStorage.create(contact);
                            } catch (ExecutionException | InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onClick: " + dbStorage.toString());

                        })
                .setNegativeButton("cancel",
                        (dialog1, id) -> dialog1.cancel());
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


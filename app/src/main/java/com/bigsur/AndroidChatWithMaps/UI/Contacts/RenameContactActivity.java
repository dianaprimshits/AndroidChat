package com.bigsur.AndroidChatWithMaps.UI.Contacts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;

public class RenameContactActivity  extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG =  "LOG";
    DataWithIconManager dbManager = ViewableContactManager.getInstance();
    private static int DEFAULT_VALUE = -1;
    String contactName;
    int contactId;
    EditText contactNewNameET;
    ImageButton okButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_rename_activity);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("id", DEFAULT_VALUE);
        contactName = intent.getStringExtra("name");

        contactNewNameET = (EditText) findViewById(R.id.renameContactActFirstNameET);
        contactNewNameET.setText(dbManager.getById(contactId).getName());
        okButton = (ImageButton) findViewById(R.id.renameContactActButtonOk);



        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.renameContactActButtonOk:
                Contacts contacts = (Contacts) dbManager.getById(contactId);
                Log.d(TAG, "onClick: !!!!!!!!!!!!!!!!!!!!!!"+contacts.getId());
                Log.d(TAG, "onClick: !!!!!!!!!!!!!!!!!!!!!!" + contacts.getName());
                Log.d(TAG, "onClick: !!!!!!!!!!!!!!!!!!!!!!" + contactNewNameET.getText());
                Log.d(TAG, "onClick: !!!!!!!!!!!!!!!!!!!!!!" + new Contacts(contactNewNameET.getText().toString(), contacts.getPhoneNumber()).toString());
                dbManager.update(new Contacts(contactNewNameET.getText().toString(), contacts.getPhoneNumber()));
                onBackPressed();
        }
    }
}

package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableChat.ViewableChatManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

public class RenameChatActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG =  "LOG";
    DataWithIconManager dbManager = ViewableChatManager.getInstance();
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

        contactNewNameET = findViewById(R.id.renameContactActFirstNameET);
        contactNewNameET.setText(dbManager.getById(contactId).getName());
        okButton = findViewById(R.id.renameContactActButtonOk);



        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.renameContactActButtonOk:
                DataWithIcon chat = dbManager.getById(contactId);
                chat.setName(contactNewNameET.getText().toString());
                dbManager.update(chat);
                onBackPressed();
        }
    }
}

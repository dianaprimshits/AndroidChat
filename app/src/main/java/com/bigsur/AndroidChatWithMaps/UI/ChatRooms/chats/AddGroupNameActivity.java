package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.AdapterForContactsInChat;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;

import java.util.ArrayList;

public class AddGroupNameActivity  extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DataWithIconManager dbStorage = ViewableContactManager.getInstance();
    AdapterForContactsInChat adapter;
    DataWithIconListview lvMain;
    ImageView backBt;
    ImageView groupAvatar;
    EditText groupName;
    ArrayList<Integer> contactsId = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_name);


        toolbar = findViewById(R.id.group_name_add_act_toolbar);
        lvMain = findViewById(R.id.lvMain);
        backBt = findViewById(R.id.groupAddNameActBtBack);
        groupAvatar = findViewById(R.id.groupAvatar);
        groupName = findViewById(R.id.groupNameET);

        backBt.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(null);

        Intent intent = getIntent();
        int contactsInGroup = intent.getIntExtra("numberOfContacts", -1);

        for(int i = 0; i < contactsInGroup; i++) {
            contactsId.add(intent.getIntExtra("contactId" + i, -1));
        }

        adapter = new AdapterForContactsInChat(getApplicationContext(), dbStorage, contactsId);
        lvMain.init(dbStorage, adapter, "contacts", this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.groupAddNameActBtBack:
                onBackPressed();
                break;
            case R.id.createGroup:

        }

    }
}

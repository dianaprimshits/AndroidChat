package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms.SQLiteContactsChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.DB.Messages.SQLiteMessagesManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableChat.ViewableChatManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.AdapterForContactsInChat;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;

import java.util.ArrayList;

public class DialogInfoActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DataWithIconManager dbStorage = ViewableContactManager.getInstance();
    AdapterForContactsInChat adapter;
    DataWithIconListview lvMain;
    ImageView backBt;
    ImageView groupAvatar;
    TextView groupNameTV;
    TextView groupMembersTV;
    ArrayList<Integer> contactsId = new ArrayList<>();
    SQLiteContactsChatRoomsManager contactsChatRoomsManager = new SQLiteContactsChatRoomsManager();
    DataWithIconManager chatRoomManager = ViewableChatManager.getInstance();
    SQLiteMessagesManager messagesManager = SQLiteMessagesManager.getInstance();
    int contactsInGroup;
    int chatRoomId;
    String chatName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_info_activity);


        toolbar = findViewById(R.id.dialog_info_toolbar);
        lvMain = findViewById(R.id.lvMain);
        backBt = findViewById(R.id.dialogInfoActBtBack);
        groupAvatar = findViewById(R.id.dialogInfoAvatar);
        groupNameTV = findViewById(R.id.dialogInfoChatName);
        groupMembersTV = findViewById(R.id.dialogInfoMembersInChat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();

        contactsInGroup = intent.getIntExtra("membersNumber", -1);
        chatRoomId = intent.getIntExtra("chatRoomId", -1);
        chatName = intent.getStringExtra("chatName");


        groupMembersTV.setText(contactsInGroup + "contacts");
        groupNameTV.setText(chatName);

        for (int i = 0; i < contactsInGroup; i++) {
            contactsId.add(intent.getIntExtra("contactId" + i, -1));
        }

        adapter = new AdapterForContactsInChat(getApplicationContext(), dbStorage, contactsId);
        lvMain.init(dbStorage, adapter, "contacts", this);

        backBt.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.dialogInfoActBtBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_dialog_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dialogMenuItemRename:
                Intent renameIntent = new Intent(this, RenameChatActivity.class);
                renameIntent.putExtra("id", chatRoomId);
                renameIntent.putExtra("name", chatRoomManager.getById(chatRoomId).getName());
                startActivity(renameIntent);
                break;
            case R.id.dialogMenuItemDelete:
                chatRoomManager.delete(chatRoomId,
                        () -> Toast.makeText(getApplicationContext(), "Chat deleted", Toast.LENGTH_SHORT).show(),
                        () -> Toast.makeText(getApplicationContext(), "Chat can't be deleted", Toast.LENGTH_SHORT).show());
                messagesManager.deleteByChatRoomId(chatRoomId);
                contactsChatRoomsManager.deleteByChatRoomId(chatRoomId);
                break;
        }
        return true;
    }
}
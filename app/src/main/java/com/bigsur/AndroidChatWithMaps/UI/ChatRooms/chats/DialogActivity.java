package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.SQLiteChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms.ContactsChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms.SQLiteContactsChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DB.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DB.Messages.Messages;
import com.bigsur.AndroidChatWithMaps.DB.Messages.SQLiteMessagesManager;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.view.View.OnClickListener;

public class DialogActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "!!!!!LOG!!!!!";
    AuthenticationManager authManager = AuthenticationManager.getInstance();
    int yourId;
    SQLiteChatRoomsManager chatRoomManager = SQLiteChatRoomsManager.getInstance();
    SQLiteMessagesManager messagesManager = SQLiteMessagesManager.getInstance();
    SQLiteContactsChatRoomsManager contactsChatRoomsManager = new SQLiteContactsChatRoomsManager();
    ImageButton buttonBack;
    EditText messageET;
    ImageButton buttonSend;
    TextView contactNameTV;
    TextView numberOfChatMembers;

    int contactOrChatRoomId;
    int contactId;
    int chatRoomId;

    int numberOfMembers;

    String dialogName;
    String intentComingFrom;
    ArrayList<Integer> contactsId = new ArrayList<>();
    Integer contactsInGroup;

    private static int DEFAULT_VALUE = -1;
    ListView messagesLV;
    MessageAdapter adapter;
    Toolbar toolbar;
    ConstraintLayout toolbarInfoLL;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        yourId = authManager.getUserId();
        Intent intent = getIntent();
        dialogName = getIntent().getStringExtra("name");
        contactOrChatRoomId = intent.getIntExtra("id", DEFAULT_VALUE);
        intentComingFrom = intent.getStringExtra("coming from");


        contactsInGroup = intent.getIntExtra("numberOfContacts", -1);

        toolbar = findViewById(R.id.dialog_toolbar);
        buttonBack = findViewById(R.id.dialogActivityToolbarButtonBack);
        messageET = findViewById(R.id.dialogActivityMessageET);
        buttonSend = findViewById(R.id.dialogActivityMessageSendButton);
        contactNameTV = findViewById(R.id.dialogActivityToolbarContactName);
        messagesLV = findViewById(R.id.lvMessage);
        numberOfChatMembers = findViewById(R.id.numberOfChatMembers);
        toolbarInfoLL = findViewById(R.id.dialogTolbarInfoLL);

        contactNameTV.setText(dialogName);
        Log.d(TAG, "CHAT ROOM ID  " + contactOrChatRoomId);
        Log.d(TAG, "COMING FROM  " + intentComingFrom);

        toolbarInfoLL.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        buttonSend.setOnClickListener(this);

        try {
            if (intentComingFrom.equals("chatRooms")) {
                chatRoomId = contactOrChatRoomId;
                messagesDisplay(chatRoomId);
            } else if (intentComingFrom.equals("contacts")) {
                contactId = contactOrChatRoomId;

                if (getContactChatConnectionNumber(contactId) > 0) {
                    chatRoomId = getChatRoomId(contactId);
                    messagesDisplay(chatRoomId);
                }
            } else if (intentComingFrom.equals("groupCreation")) {
                chatRoomId = chatRoomManager.getLastId() + 1;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        if (contactsInGroup != -1 && contactsInGroup != null) {
            for (int i = 0; i < contactsInGroup; i++) {
                contactsId.add(intent.getIntExtra("id" + i, -1));
            }
        } else {
            contactsInGroup = contactsChatRoomsManager.getContactsNumber(chatRoomId);
            ArrayList<ContactsChatRooms> contacts = contactsChatRoomsManager.getByChatRoomId(chatRoomId);

            for (int i = 0; i < contacts.size(); i++) {
                contactsId.add(contacts.get(i).getContactId());
            }
        }



        numberOfMembers = contactsChatRoomsManager.getContactsNumber(chatRoomId);
        if(numberOfMembers > 1) {
            numberOfChatMembers.setText((contactsChatRoomsManager.getContactsNumber(chatRoomId) + 1) + " members");
        }

        setSupportActionBar(toolbar);
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
            case  R.id.dialogMenuItemDelete:
                chatRoomManager.delete(chatRoomId);
                messagesManager.deleteByChatRoomId(chatRoomId);
                contactsChatRoomsManager.deleteByChatRoomId(chatRoomId);
                break;
        }

        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogActivityToolbarButtonBack:
                onBackPressed();
                break;
            case R.id.dialogActivityMessageSendButton:
                messageET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.MAX_VALUE - 1)});

                if (messageET.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Input message", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (intentComingFrom.equals("groupCreation")) {
                    try {
                        ChatRooms chatRoom = new ChatRooms(dialogName);
                        chatRoomManager.create(chatRoom);
                        chatRoomId = chatRoomManager.getLastId();

                        for (int i = 0; i < contactsInGroup; i++) {
                            ContactsChatRooms connection = new ContactsChatRooms(contactsId.get(i), chatRoomId);
                            contactsChatRoomsManager.create(connection);
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (intentComingFrom.equals("contacts")) {
                    try {
                        if (getContactChatConnectionNumber(contactId) == 0) {
                            ChatRooms chatRoom = new ChatRooms(dialogName);
                            chatRoomManager.create(chatRoom);
                            chatRoomId = chatRoomManager.getLastId();

                            ContactsChatRooms connection = new ContactsChatRooms(contactId, chatRoomId);
                            contactsChatRoomsManager.create(connection);
                        }

                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Messages newMessage = new Messages(messageET.getText().toString(),
                        new Date(),
                        yourId,
                        chatRoomId);
                messagesManager.create(new DataFromDB(newMessage));
                Log.d("!!!!!!LOG!!!!!!!", "onClick: " + newMessage.getMessage());
                messageET.setText("");
                try {
                    messagesDisplay(chatRoomId);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.dialogTolbarInfoLL:
                Intent infoIntent = new Intent(this, DialogInfoActivity.class);


                for(int i = 0; i < contactsId.size(); i++) {
                    infoIntent.putExtra("contactId" + i, contactsId.get(i));
                }

                infoIntent.putExtra("chatName", dialogName);
                infoIntent.putExtra("membersNumber", numberOfMembers);
                infoIntent.putExtra("chatRoomId", chatRoomId);
                startActivity(infoIntent);
                break;
        }

    }


    public int getContactChatConnectionNumber(int contactId) throws ExecutionException, InterruptedException {
        List<ContactsChatRooms> result = contactsChatRoomsManager.getByContactId(contactId);
        if (result.isEmpty()) {
            return 0;
        }
        return result.size();
    }

    public ArrayList<ContactsChatRooms> getChatsWithGivenContact(int contactId) throws ExecutionException, InterruptedException {
        List<ContactsChatRooms> result = contactsChatRoomsManager.getByContactId(contactId);
        ArrayList<ContactsChatRooms> contactEntries = new ArrayList<>();

        for (ContactsChatRooms item : result) {
            contactEntries.add(item);
        }
        return contactEntries;
    }


    public int getChatRoomId(int contactId) throws ExecutionException, InterruptedException {
        ArrayList<ContactsChatRooms> chatRoomsWithGivenContacts = getChatsWithGivenContact(contactId);
        ArrayList<Integer> chatRoomIDs = new ArrayList<>();

        for (ContactsChatRooms connection : chatRoomsWithGivenContacts) {
            chatRoomIDs.add(connection.getChatRoomId());
        }

        for (int chatRoomId : chatRoomIDs) {
            int contactsNumber = contactsChatRoomsManager.getContactsNumber(chatRoomId);

            if (contactsNumber == 1) {
                return chatRoomId;
            }
        }
        return -1;
    }


    public void messagesDisplay(int chatRoomId) throws ExecutionException, InterruptedException {

        List<DataFromDB> result = messagesManager.getByChatRoomId(chatRoomId);
        ArrayList<Messages> messagesToDisplay = new ArrayList<>();

        for (DataFromDB item : result) {
            messagesToDisplay.add((Messages) item.getData());
        }

        adapter = new MessageAdapter(this, messagesToDisplay);
        messagesLV.setAdapter(adapter);
    }

}

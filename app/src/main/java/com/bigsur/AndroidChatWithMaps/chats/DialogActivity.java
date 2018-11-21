package com.bigsur.AndroidChatWithMaps.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.DBManager.Adapters.MessageAdapter;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ContactsChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteContactsChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteMessagesManager;
import com.bigsur.AndroidChatWithMaps.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.view.View.OnClickListener;

public class DialogActivity extends AppCompatActivity implements OnClickListener {
    private static final String TAG = "!!!!!LOG!!!!!";
    AuthenticationManager authManager = AuthenticationManager.getInstance();
    int yourId;
    SQLiteChatRoomsManager chatRoomManager = new SQLiteChatRoomsManager();
    SQLiteMessagesManager messagesManager = new SQLiteMessagesManager();
    SQLiteContactsChatRoomsManager contactsChatRoomsManager = new SQLiteContactsChatRoomsManager();
    ImageButton buttonBack;
    EditText messageET;
    ImageButton buttonSend;
    TextView contactNameTV;

    int contactOrChatRoomId;
    int contactId;
    int chatRoomId;
    String dialogName;
    String intentComingFrom;

    private static int DEFAULT_VALUE = -1;
    ListView messagesLV;
    MessageAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        //getting id from pref
        yourId = authManager.getUserId();

        //getting data, coming with intent
        Intent intent = getIntent();
        dialogName = getIntent().getStringExtra("name");
        contactOrChatRoomId = intent.getIntExtra("id", DEFAULT_VALUE);
        intentComingFrom = intent.getStringExtra("coming from");


        buttonBack = (ImageButton) findViewById(R.id.dialogActivityToolbarButtonBack);
        messageET = (EditText) findViewById(R.id.dialogActivityMessageET);
        buttonSend = (ImageButton) findViewById(R.id.dialogActivityMessageSendButton);
        contactNameTV = (TextView) findViewById(R.id.dialogActivityToolbarContactName);
        messagesLV = (ListView) findViewById(R.id.lvMessage);

        contactNameTV.setText(dialogName);
        Log.d(TAG, "CHAT ROOM ID  " + contactOrChatRoomId);
        Log.d(TAG, "COMING FROM  " + intentComingFrom);


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
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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

                if (intentComingFrom.equals("contacts")) {
                    try {
                        if (getContactChatConnectionNumber(contactId) == 0) {
                            ChatRooms chatRoom = new ChatRooms(dialogName);
                            chatRoomManager.create(new DataFromDB(chatRoom));
                            //говнокод. нужно посмотреть айдишник только что созданного в бд чата
                            //ищу через MAX по id стобцу
                            int lastId = chatRoomManager.getLastId();

                            ContactsChatRooms connection = new ContactsChatRooms(contactId, lastId);
                            contactsChatRoomsManager.create(new DataFromDB(connection));
                        }
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Messages newMessage = new Messages(messageET.getText().toString(),
                                new SimpleDateFormat("h:mm a").format(new Date()),
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
        }

    }


    public int getContactChatConnectionNumber(int contactId) throws ExecutionException, InterruptedException {
        List<DataFromDB> result = contactsChatRoomsManager.getByContactId(contactId);
        if (result.isEmpty()) {
            return 0;
        }
        return result.size();
    }

    public ArrayList<ContactsChatRooms> getChatsWithGivenContact(int contactId) throws ExecutionException, InterruptedException {
        List<DataFromDB> result = contactsChatRoomsManager.getByContactId(contactId);
        ArrayList<ContactsChatRooms> contactEntries = new ArrayList<>();

        for (DataFromDB item : result) {
            contactEntries.add((ContactsChatRooms) item.getData());
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

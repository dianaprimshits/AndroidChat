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
    int chatRoomId;
    String contactOrChatName;
    String intentComingFrom;
    private static int MAX_MESSAGE_LENGTH = Integer.MAX_VALUE - 1;
    private static int DEFAULT_VALUE = -1;
    ArrayList<Messages> messages = new ArrayList<>();
    ListView messagesLV;
    MessageAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        yourId = authManager.getUserId();

        buttonBack = (ImageButton) findViewById(R.id.dialogActivityToolbarButtonBack);
        messageET = (EditText) findViewById(R.id.dialogActivityMessageET);
        buttonSend = (ImageButton) findViewById(R.id.dialogActivityMessageSendButton);
        contactNameTV = (TextView) findViewById(R.id.dialogActivityToolbarContactName);
        messagesLV = (ListView) findViewById(R.id.lvMessage);

        Intent intent = getIntent();
        contactOrChatName = getIntent().getStringExtra("contactName");
        contactOrChatRoomId = intent.getIntExtra("id", DEFAULT_VALUE);
        intentComingFrom = intent.getStringExtra("coming from");

        try {
            chatRoomId = getChatRoomId();
            messagesDisplay(chatRoomId);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        contactNameTV.setText(contactOrChatName);
        Log.d(TAG, "CHAT ROOM ID  " + contactOrChatRoomId);
        Log.d(TAG, "COMING FROM  " + intentComingFrom);


        buttonBack.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialogActivityToolbarButtonBack:
                onBackPressed();
                break;
            case R.id.dialogActivityMessageSendButton:
                //create chat, create message.

                messageET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Integer.MAX_VALUE - 1)});

                if (messageET.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Input message", Toast.LENGTH_SHORT).show();
                    return;
                }


                ChatRooms chatRoom = new ChatRooms(contactOrChatName);
                Log.d("!!!!!!LOG!!!!!!!", "onClick: " + chatRoom.getName());

                Messages newMessage = new Messages(messageET.getText().toString(),
                        new SimpleDateFormat("h:mm a").format(new Date()),
                        yourId,
                        chatRoomId);
                messages.add(newMessage);
                Log.d("!!!!!!LOG!!!!!!!", "onClick: " + newMessage.getMessage());
                messageET.setText("");
        }

    }

    public int getChatRoomId() throws ExecutionException, InterruptedException {
        if (intentComingFrom.equals("contacts")) {
            List<DataFromDB> result = contactsChatRoomsManager.getByContactId(contactOrChatRoomId);
            ArrayList<ContactsChatRooms> contactEntries = new ArrayList<>();

            for (DataFromDB item : result) {
                contactEntries.add((ContactsChatRooms) item.getData());
            }

            int entriesNumber = contactEntries.size();

            if (entriesNumber == 0) {
                return chatRoomCreating().getId();
            }
            if (entriesNumber == 1) {
                return contactEntries.get(0).getChatRoomId();
            }
        }
        return contactOrChatRoomId;
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


    public ChatRooms chatRoomCreating() {
        ChatRooms chatRoom = new ChatRooms(contactOrChatName);
        chatRoomManager.create(new DataFromDB<>(chatRoom));
        ContactsChatRooms connection = new ContactsChatRooms(contactOrChatRoomId, chatRoom.getId());
        contactsChatRoomsManager.create(new DataFromDB<>(connection));
        return chatRoom;
    }
}

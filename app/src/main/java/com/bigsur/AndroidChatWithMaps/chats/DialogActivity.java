package com.bigsur.AndroidChatWithMaps.chats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogActivity extends AppCompatActivity {
    SQLiteChatRoomsManager chatRoomManager = new SQLiteChatRoomsManager();
    ImageButton buttonBack;
    EditText messageET;
    ImageButton buttonSend;
    TextView contactName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        buttonBack = (ImageButton) findViewById(R.id.dialogActivityToolbarButtonBack);
        messageET = (EditText) findViewById(R.id.dialogActivityMessageET);
        buttonSend = (ImageButton) findViewById(R.id.dialogActivityMessageSendButton);
        contactName = (TextView) findViewById(R.id.dialogActivityToolbarContactName);

        contactName.setText(getIntent().getStringExtra("contactName"));


        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dialogActivityToolbarButtonBack:
                        onBackPressed();
                        break;
                    case R.id.dialogActivityMessageSendButton:
                        //create chat, create message.
                        SimpleDateFormat time = new SimpleDateFormat("h:mm a");
                        String currentDateTime = time.format(new Date());
                        ChatRooms chatRoom = new ChatRooms(contactName.getText().toString(), messageET.getText().toString(),currentDateTime);
                        Log.d("!!!!!!LOG!!!!!!!", "onClick: "+ chatRoom.toString());

                }
            }
        };

        buttonBack.setOnClickListener(onClick);
        buttonSend.setOnClickListener(onClick);
    }
}

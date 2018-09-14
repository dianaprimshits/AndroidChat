package com.bigsur.AndroidChatWithMaps.DBManager.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = Contacts.class,
                parentColumns = "_id",
                childColumns = "contact_id",
                onDelete = CASCADE),
        @ForeignKey(entity = ChatRooms.class,
                parentColumns = "chat_room_id",
                childColumns = "chat_room_id",
                onDelete = CASCADE)
},
        indices = {@Index(value = "message_id")})

public class Messages {
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "message_id")public int messageId;
    private String message;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "contact_id")
    private int contactId;
    @ColumnInfo(name = "chat_room_id")
    private int chatRoomId;


    public Messages(String message, String date, int contactId, int chatRoomId) {
        this.message = message;
        this.date = date;
        this.contactId = contactId;
        this.chatRoomId = chatRoomId;
    }

    public int getId() {
        return messageId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public int getContactId() {
        return contactId;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }


    public String toString() {
        return String.format("\n Message id: %d \n Message text: %s \n Message created at: %t \n Contact id: %d. \n Chat room id: %d",
                getId(), getMessage(), getDate(), getContactId(), getChatRoomId());
    }
}

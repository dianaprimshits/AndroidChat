package com.bigsur.AndroidChatWithMaps.DBManager.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "chat_rooms",
        indices = {@Index(value = "chat_room_id",
                          unique = true)},
        foreignKeys = {
                @ForeignKey(entity = Messages.class,
                        parentColumns = "message",
                        childColumns = "last_message"),
                @ForeignKey(entity = Messages.class,
                        parentColumns = "date",
                        childColumns = "last_message_date")
                      }
        )

public class ChatRooms implements DataWithIcon {
    @ColumnInfo(name = "chat_room_id")
    @PrimaryKey(autoGenerate = true)
    public int chatRoomId;

    public String getChatRoomName() {
        return chatRoomName;
    }

    @ColumnInfo(name = "chat_room_name")
    private String chatRoomName;
    @ColumnInfo(name = "chat_avatar")
    private int chatAvatar;
    @ColumnInfo(name = "last_message")
    String lastMessage;
    @ColumnInfo(name = "last_message_date")
    String lastMessageDate;


    public ChatRooms( String chatRoomName, String lastMessage, String lastMessageDate) {
       this.chatRoomName = chatRoomName;
       this.lastMessage = lastMessage;
       this.lastMessageDate = lastMessageDate;
    }


    public int getChatAvatar() {
        return chatAvatar;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public int getId() {
        return chatRoomId;
    }

    @Override
    public String getName() {
        return chatRoomName;
    }

    @Override
    public String getSubname() {
        return null;
    }

    @Override
    public int getAvatar() {
        return 0;
    }

    @Override
    public int getDate() {
        return 0;
    }



    public void setChatAvatar(int chatAvatar) {
        this.chatAvatar = chatAvatar;
    }


    public String toString(){
        return String.format("Chat Room's id: %d \n chatRoomName: %s \n chatRoomLastMessageDate: %s \n chatRoomLastMessage %s",
                getId(), getName(), getLastMessageDate(), getLastMessage());
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }
}

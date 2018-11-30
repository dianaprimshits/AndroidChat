package com.bigsur.AndroidChatWithMaps.DB.ChatRooms;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIcon;

@Entity(tableName = "chat_rooms",
        indices = {@Index(value = "chat_room_id",
                unique = true)}
)

public class ChatRooms implements DataWithIcon {
    @ColumnInfo(name = "chat_room_id")
    @PrimaryKey(autoGenerate = true)
    public int chatRoomId;
    @ColumnInfo(name = "chat_room_name")
    private String chatRoomName;
    @ColumnInfo(name = "chat_avatar")
    private int chatAvatar;


    public ChatRooms(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }


    public int getId() {
        return chatRoomId;
    }


    @Override
    public String getName() {
        return chatRoomName;
    }
    public String getChatRoomName() {
        return getName();
    }

    public int getChatAvatar() {
        return chatAvatar;
    }


    @Override
    public int getAvatar() {
        return 0;
    }

    @Override
    public String getExtraTitle() {
        return "";
    }


    @Override
    public String getSubname() {
        //do nothing
        return null;
    }


    public String toString(){
        return String.format("Chat Room's id: %d \n chatRoomName: %s",
                getId(), getName());
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }


    public void setChatAvatar(int chatAvatar) {
        this.chatAvatar = chatAvatar;
    }
}
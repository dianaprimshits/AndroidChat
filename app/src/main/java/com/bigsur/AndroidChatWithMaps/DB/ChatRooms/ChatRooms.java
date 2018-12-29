package com.bigsur.AndroidChatWithMaps.DB.ChatRooms;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.Date;

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
    private String chatAvatar;


    public ChatRooms(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public ChatRooms(ChatRooms chatRooms) {
        this.chatRoomName = chatRooms.getName();
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

    public String getChatAvatar() {
        return chatAvatar;
    }


    @Override
    public String getAvatar() {
        return null;
    }

    @Override
    public Date getExtraTitle() {
        return null;
    }

    @Override
    public Boolean getExtraTitleIcon() {
        return null;
    }

    @Override
    public void setName(String name) {
        setChatRoomName(name);
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


    public void setChatAvatar(String chatAvatar) {
        this.chatAvatar = chatAvatar;
    }
}
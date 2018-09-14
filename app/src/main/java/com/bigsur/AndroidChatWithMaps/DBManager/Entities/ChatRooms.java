package com.bigsur.AndroidChatWithMaps.DBManager.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "chat_rooms",
        indices = {@Index(value = "name",
                          unique = true)})

public class ChatRooms{
    @ColumnInfo(name = "chat_room_id")
    @PrimaryKey(autoGenerate = true)
    public int chatRoomId;
    String name;

    public ChatRooms(String name) {
        this.name = name;
    }

    public int getId() {
        return chatRoomId;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return String.format("Chat Room's id: %d \n name: %s.",
                getId(), getName());
    }

    public void setName(String name) {
        this.name = name;
    }
}

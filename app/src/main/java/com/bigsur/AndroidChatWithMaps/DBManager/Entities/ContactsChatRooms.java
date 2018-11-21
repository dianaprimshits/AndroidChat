package com.bigsur.AndroidChatWithMaps.DBManager.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "connections",
        primaryKeys = {"contact_id", "chat_id"},
        foreignKeys = {
        @ForeignKey(entity = Contacts.class,
                parentColumns = "_id",
                childColumns = "contact_id",
                onDelete = CASCADE),
        @ForeignKey(entity = ChatRooms.class,
                parentColumns = "chat_room_id",
                childColumns = "chat_id",
                onDelete = CASCADE)},
        indices = {@Index(value = {"contact_id", "chat_id"},
                unique = true)})
public class ContactsChatRooms {
    @ColumnInfo(name = "contact_id")
    public final int contactId;
    @ColumnInfo(name = "chat_id")
    public final int chatRoomId;

    public ContactsChatRooms(final int contactId, final int chatRoomId) {
        this.contactId = contactId;
        this.chatRoomId = chatRoomId;
    }

    public int getContactId() {
        return contactId;
    }

    public int getChatRoomId() {
        return chatRoomId;
    }
}

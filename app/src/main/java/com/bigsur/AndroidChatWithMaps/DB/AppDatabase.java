package com.bigsur.AndroidChatWithMaps.DB;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRoomDAO;
import com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms.ContactsChatRoomsDAO;
import com.bigsur.AndroidChatWithMaps.DB.Contacts.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DB.Messages.MessagesDAO;
import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms.ContactsChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.Messages.Messages;



@Database(entities = {Contacts.class, ChatRooms.class, Messages.class, ContactsChatRooms.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactsDAO getContactsDao();
    public abstract ChatRoomDAO getChatRoomDao();
    public abstract MessagesDAO getMessagesDao();
    public abstract ContactsChatRoomsDAO getContactsChatRoomsDao();

}

package com.bigsur.AndroidChatWithMaps.DBManager;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ChatRoomDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.MessagesDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;

@Database(entities = {Contacts.class, ChatRooms.class, Messages.class}, version = 10)
public abstract class AppDatabase<T> extends RoomDatabase {

    public abstract ContactsDAO getContactsDao();
    public abstract ChatRoomDAO getChatRoomDao();
    public abstract MessagesDAO getMessagesDao();

}

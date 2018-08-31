package com.bigsur.AndroidChatWithMaps.DBManager;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Contacts.class/*, Messages.class*/}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactsDAO getContactsDao();
  //  public abstract MessagesDAO getMessagesDao();

}

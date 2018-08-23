package com.bigsur.AndroidChatWithMaps.DBManager;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Contacts.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactsDAO getContactsDao();
}

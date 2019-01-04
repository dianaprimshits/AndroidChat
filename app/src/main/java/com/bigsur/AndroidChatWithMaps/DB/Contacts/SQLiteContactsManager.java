package com.bigsur.AndroidChatWithMaps.DB.Contacts;


import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class SQLiteContactsManager {
    private static String TAG = "!!!LOG!!!";

    static SQLiteContactsManager instance;

    private SQLiteContactsManager() {
    }


    public static SQLiteContactsManager getInstance() {
        if (instance == null) {
            instance = new SQLiteContactsManager();
        }
        return instance;
    }


    public FutureTask<Contacts> taskGetContactById(int id) {
        FutureTask<Contacts> getContactById = new FutureTask<>(new Callable<Contacts>() {
            @Override
            public Contacts call() throws Exception {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                return contactsDao.getByID(id);
            }
        });
        return getContactById;
    }


    public FutureTask<ArrayList<Contacts>> taskGetAllContacts() {
        FutureTask<ArrayList<Contacts>> onFail = new FutureTask<>(new Callable<ArrayList<Contacts>>() {
            @Override
            public ArrayList<Contacts> call() {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getAll().toString());
                return new ArrayList<>(contactsDao.getAll());
            }
        });
        return onFail;
    }


    public FutureTask<Void> taskCreateContact(Contacts contact) {
        FutureTask<Void> createContact = new FutureTask<>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.insert(contact);
                return null;
            }
        });
        return createContact;
    }


    public FutureTask<Void> taskUpdateContact(Contacts contact) {
        FutureTask<Void> updateContact = new FutureTask<>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.update(contact);
                return null;
            }
        });
        return updateContact;
    }


    public FutureTask<Void> taskDeleteContactById(int id) {
        FutureTask<Void> deleteContactById = new FutureTask<Void>(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.delete(id);
                return null;
            }
        });
        return deleteContactById;
    }


    public FutureTask<Contacts> taskGetLastContact() {
        FutureTask<Contacts> getLastContact = new FutureTask<>(new Callable<Contacts>() {
            @Override
            public Contacts call() throws Exception {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                return contactsDao.getLastContact();
            }
        });
        return getLastContact;
    }
}






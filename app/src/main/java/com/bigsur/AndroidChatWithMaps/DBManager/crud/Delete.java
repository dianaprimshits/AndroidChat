package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;

import com.bigsur.AndroidChatWithMaps.DBManager.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.ContactsDAO;

public class Delete extends AsyncTask<Contacts, Void, Void>{

    @Override
    protected Void doInBackground(Contacts... params) {
        AppDatabase db = App.getInstance().getDatabase();
        ContactsDAO contactsDao = db.getContactsDao();
        contactsDao.delete(params[0].getId());
        return null;
    }
}

package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;

import com.bigsur.AndroidChatWithMaps.DBManager.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.ContactsDAO;

public class GetById extends AsyncTask<Contacts, Void, Contacts>{

    @Override
    protected Contacts doInBackground(Contacts... params) {
        AppDatabase db = App.getInstance().getDatabase();
        ContactsDAO contactsDao = db.getContactsDao();
        int id = params[0].getId();
        return contactsDao.getByID(id);
    }

    @Override
    protected void onPostExecute(Contacts contact) {
        super.onPostExecute(contact);
    }
}

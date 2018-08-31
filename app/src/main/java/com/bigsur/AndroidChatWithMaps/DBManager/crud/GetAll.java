package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.DBManager.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.ContactsDAO;

public class GetAll extends AsyncTask<Contacts, Void, Cursor>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Cursor doInBackground(Contacts... contacts) {
        AppDatabase db = App.getInstance().getDatabase();
        ContactsDAO contactsDao = db.getContactsDao();

        Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getAll().toString());
        return contactsDao.getAll();
    }


    @Override
    protected void onPostExecute(Cursor dialogs) {
        super.onPostExecute(dialogs);
    }
}

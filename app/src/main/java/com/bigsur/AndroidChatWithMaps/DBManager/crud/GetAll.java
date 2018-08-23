package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.DBManager.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.ContactsDAO;
import java.util.List;

public class GetAll extends AsyncTask<Contacts, Void, List<Contacts>>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Contacts> doInBackground(Contacts... contacts) {
        AppDatabase db = App.getInstance().getDatabase();
        ContactsDAO contactsDao = db.getContactsDao();

        Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getAll().toString());
        return contactsDao.getAll();
    }


    @Override
    protected void onPostExecute(List<Contacts> dialogs) {
        super.onPostExecute(dialogs);
    }
}

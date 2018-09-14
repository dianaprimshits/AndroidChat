package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;

public class GetById extends AsyncTask<Integer, Void, DataFromDB>{

    @Override
    protected DataFromDB doInBackground(Integer... params) {
        AppDatabase db = App.getInstance().getDatabase();
        ContactsDAO contactsDao = db.getContactsDao();

        int id = params[0];
        Contacts c = contactsDao.getByID(id);
        return new DataFromDB(c);
    }

    @Override
    protected void onPostExecute(DataFromDB contact) {
        super.onPostExecute(contact);
    }
}

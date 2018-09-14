package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;

public class Delete<T extends  Object> extends AsyncTask<Integer, Void, Void>{

    @Override
    protected Void doInBackground(Integer... params) {
        AppDatabase db = App.getInstance().getDatabase();

        ContactsDAO contactsDao = db.getContactsDao();

        contactsDao.delete(params[0]);
        return null;
    }
}

package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;

import java.util.ArrayList;
import java.util.List;

public class GetAll extends AsyncTask<Void, Void, List<DataFromDB>>{

    @Override
    protected List<DataFromDB> doInBackground(Void ... params) {
        ArrayList<DataFromDB> displayList = new ArrayList<>();

        AppDatabase db = App.getInstance().getDatabase();

        ContactsDAO contactsDao = db.getContactsDao();

        Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getAll().toString());
        List<Contacts> contactsList = contactsDao.getAll();

        for(int i = 0; i < contactsList.size(); i++) {
            displayList.add(new DataFromDB(contactsList.get(i)));
        }
        return displayList;
    }


    @Override
    protected void onPostExecute(List<DataFromDB> contacts) {
        super.onPostExecute(contacts);
    }
}

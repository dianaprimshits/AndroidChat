package com.bigsur.AndroidChatWithMaps.DBManager;


import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class SetupDatabase extends AsyncTask<Contacts, Void, ArrayList<Contacts>>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Contacts> doInBackground(Contacts... contacts) {
        AppDatabase db = App.getInstance().getDatabase();
        ContactsDAO contactsDao = db.getContactsDao();

        ArrayList<Contacts> dialogsList = new ArrayList<Contacts>(){};

        switch (contacts.length) {
            case 1:
                if (contactsDao.getByID(contacts[0].getId()) == null) {
                     contactsDao.insert(contacts[0]);
                } else {
                     contactsDao.delete(contacts[0]);
                }
                break;
            case 0:
                dialogsList = (ArrayList<Contacts>)contactsDao.getAll();
                break;
            default:
                return null;
        }
        Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getAll().toString());
        return dialogsList;
    }


    @Override
    protected void onPostExecute(ArrayList<Contacts> dialogs) {
        super.onPostExecute(dialogs);
    }
}

package com.bigsur.AndroidChatWithMaps.DBManager;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteContactsManager implements StorageManager {

    @Override
    public void create(final DataFromDB data) {
        new AsyncTask<DataFromDB, Void, Void>() {
            @Override
            protected Void doInBackground(DataFromDB... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.insert((Contacts) data[0].getData());
                return null;
            }
        }.execute(data);
    }


    @Override
    public void update(final DataFromDB data) {
        new AsyncTask<DataFromDB, Void, Void>() {
            @Override
            protected Void doInBackground(DataFromDB... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.update((Contacts) data[0].getData());
                return null;
            }
        }.execute(data);
    }


    @Override
    public void delete(final int id) {
       new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();

                int id = data[0];
                contactsDao.delete(id);
                return null;
            }
        }.execute(id);
    }


    @Override
    public DataFromDB getById(final int id) throws ExecutionException, InterruptedException {
        DataFromDB data = new AsyncTask<Integer, Void, DataFromDB>() {
            @Override
            protected DataFromDB doInBackground(Integer... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();

                int id = data[0];
                Contacts c = contactsDao.getByID(id);
                return new DataFromDB(c);
            }

            @Override
            protected void onPostExecute(DataFromDB contact) {
                super.onPostExecute(contact);
            }
        }.execute(id).get();
        return data;
    }


    @Override
    public ArrayList<DataFromDB> getAll() throws InterruptedException, ExecutionException {
        List<DataFromDB> data = new AsyncTask<Void, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Void ... data) {
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
        }.execute().get();
        return new ArrayList<>(data);
    }


    @Override
    public ArrayList<DataFromDB> getSimilarData(final String search) throws ExecutionException, InterruptedException {
        List<DataFromDB> data = new AsyncTask<String, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(String ... data) {
                ArrayList<DataFromDB> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getSimilarContacts(data[0]));
                List<Contacts> contactsList = contactsDao.getSimilarContacts(data[0]);

                for(int i = 0; i < contactsList.size(); i++) {
                    displayList.add(new DataFromDB(contactsList.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> contacts) {
                super.onPostExecute(contacts);
            }
        }.execute(search).get();
        return new ArrayList<>(data);
    }

    public int getContactsNumber() throws ExecutionException, InterruptedException {
        int data = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();

                List<Contacts> contactsList = contactsDao.getAll();

                return contactsList.size();
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
            }
        }.execute().get();
        return data;
    }
}






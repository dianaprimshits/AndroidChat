package com.bigsur.AndroidChatWithMaps.DB.Contacts;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DB.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteContactsManager extends DataWithIconManager {

    static SQLiteContactsManager instance;

    private SQLiteContactsManager() {
    }


    public static SQLiteContactsManager getInstance() {
        if (instance == null) {
            instance = new SQLiteContactsManager();
        }
        return instance;
    }

    @Override
    public void create(final DataWithIcon data) {
        new AsyncTask<DataWithIcon, Void, Void>() {
            @Override
            protected Void doInBackground(DataWithIcon... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.insert((Contacts) data[0]);
                return null;
            }
        }.execute(data);
        dataUpdated();
    }


    @Override
    public void update(final DataWithIcon data) {
        new AsyncTask<DataWithIcon, Void, Void>() {
            @Override
            protected Void doInBackground(DataWithIcon... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsDAO contactsDao = db.getContactsDao();
                contactsDao.update((Contacts) data[0]);
                return null;
            }
        }.execute(data);
        dataUpdated();
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
        dataUpdated();
    }


    @Override
    public DataWithIcon getById(final int id) {
        DataWithIcon data = null;
        try {
            data = new AsyncTask<Integer, Void, DataWithIcon>() {
                @Override
                protected DataWithIcon doInBackground(Integer... data) {
                    AppDatabase db = App.getInstance().getDatabase();
                    ContactsDAO contactsDao = db.getContactsDao();

                    int id = data[0];
                    Contacts c = contactsDao.getByID(id);
                    return c;
                }

                @Override
                protected void onPostExecute(DataWithIcon contact) {
                    super.onPostExecute(contact);
                }
            }.execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return data;
    }


    @Override
    public ArrayList<DataWithIcon> getAll() {
        List<DataWithIcon> data = null;
        try {
            data = new AsyncTask<Void, Void, List<DataWithIcon>>() {
                 @Override
                 protected List<DataWithIcon> doInBackground(Void ... data) {
                     ArrayList<DataWithIcon> displayList = new ArrayList<>();

                     AppDatabase db = App.getInstance().getDatabase();
                     ContactsDAO contactsDao = db.getContactsDao();

                     Log.d("!!!!!LOG!!", "doInBackground: " + contactsDao.getAll().toString());
                     List<Contacts> contactsList = contactsDao.getAll();

                     for(int i = 0; i < contactsList.size(); i++) {
                         displayList.add(contactsList.get(i));
                     }
                     return displayList;
                 }

                 @Override
                 protected void onPostExecute(List<DataWithIcon> contacts) {
                     super.onPostExecute(contacts);
                 }
             }.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(data);
    }


   /* @Override
    public ArrayList<DataFromDB> getSimilarData(final String search) throws ExecutionException, InterruptedException {
        @SuppressLint("StaticFieldLeak") List<DataFromDB> data = new AsyncTask<String, Void, List<DataFromDB>>() {
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
*/

    public int getContactsNumber() throws ExecutionException, InterruptedException {
        @SuppressLint("StaticFieldLeak") int data = new AsyncTask<Void, Void, Integer>() {
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






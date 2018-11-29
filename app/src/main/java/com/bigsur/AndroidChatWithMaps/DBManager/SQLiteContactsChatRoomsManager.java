package com.bigsur.AndroidChatWithMaps.DBManager;

import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DB.StorageManager;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsChatRoomsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ContactsChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteContactsChatRoomsManager implements StorageManager {

    @Override
    public void create(final DataFromDB data) {
        new AsyncTask<DataFromDB, Void, Void>() {
            @Override
            protected Void doInBackground(DataFromDB... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();
                contactsChatRoomsDao.insert((ContactsChatRooms) data[0].getData());
                return null;
            }
        }.execute(data);
    }

    @Override
    public void update(final DataFromDB data) {
        //do nothing
    }

    @Override
    public void delete(int id) {
        //do nothing
    }


    public void delete(final int contactId, final int chatId) {
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                int contactId = data[0];
                int chatId = data[1];
                contactsChatRoomsDao.delete(contactId, chatId);
                return null;
            }
        }.execute(contactId, chatId);
    }

    public void deleteByContactId(final int contactId) {
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                int contactId = data[0];
                contactsChatRoomsDao.deleteByContactsId(contactId);
                return null;
            }
        }.execute(contactId);
    }

    public void deleteByChatRoomId(final int chatRoomId) {
        new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                int chatRoomId = data[0];
                contactsChatRoomsDao.deleteByChatRoomsId(chatRoomId);
                return null;
            }
        }.execute(chatRoomId);
    }


    @Override
    public DataFromDB getById(int id) throws ExecutionException, InterruptedException {
        return null;
    }

    @Override
    public ArrayList<DataFromDB> getAll() throws InterruptedException, ExecutionException {
         List<DataFromDB> data = new AsyncTask<Void, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Void ... data) {
                ArrayList<DataFromDB> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + contactsChatRoomsDao.getAll().toString());
                List<ContactsChatRooms> contactsChatRooms = contactsChatRoomsDao.getAll();

                for(int i = 0; i < contactsChatRooms.size(); i++) {
                    displayList.add(new DataFromDB(contactsChatRooms.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> contactsChatRooms) {
                super.onPostExecute(contactsChatRooms);
            }
        }.execute().get();
        return new ArrayList<>(data);
    }

    public ArrayList<DataFromDB> getByContactId(int contactId) throws InterruptedException, ExecutionException {
        List<DataFromDB> data = new AsyncTask<Integer, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Integer ... data) {
                ArrayList<DataFromDB> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                int contactId = data[0];
                List<ContactsChatRooms> contactsChatRooms = contactsChatRoomsDao.getByContactsID(contactId);

                for(int i = 0; i < contactsChatRooms.size(); i++) {
                    displayList.add(new DataFromDB(contactsChatRooms.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> contactsChatRooms) {
                super.onPostExecute(contactsChatRooms);
            }
        }.execute(contactId).get();
        return new ArrayList<>(data);
    }

    public ArrayList<DataFromDB> getByChatRoomId(final int chatRoomId) throws InterruptedException, ExecutionException {
        List<DataFromDB> data = new AsyncTask<Integer, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Integer ... data) {
                ArrayList<DataFromDB> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                int chatRoomId = data[0];
                Log.d("!!!!!LOG!!", "doInBackground: " + contactsChatRoomsDao.getAll().toString());
                List<ContactsChatRooms> contactsChatRooms = contactsChatRoomsDao.getByChatRoomsID(chatRoomId);

                for(int i = 0; i < contactsChatRooms.size(); i++) {
                    displayList.add(new DataFromDB(contactsChatRooms.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> contactsChatRooms) {
                super.onPostExecute(contactsChatRooms);
            }
        }.execute(chatRoomId).get();
        return new ArrayList<>(data);
    }


    @Override
    public List<DataFromDB> getSimilarData(String search) throws ExecutionException, InterruptedException {
        return null;
    }

    public int getContactsNumber(int chatRoomId) throws ExecutionException, InterruptedException {
        int data = new AsyncTask<Integer, Void, Integer>() {
            @Override
            protected Integer doInBackground(Integer ... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();
                return contactsChatRoomsDao.getContactsNumber(data[0]);
            }

            @Override
            protected void onPostExecute(Integer contactsInChatRoom) {
                super.onPostExecute(contactsInChatRoom);
            }
        }.execute(chatRoomId).get();
        return data;
    }
}

package com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms;

import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteContactsChatRoomsManager {

    public void create(final ContactsChatRooms data) {
        new AsyncTask<ContactsChatRooms, Void, Void>() {
            @Override
            protected Void doInBackground(ContactsChatRooms... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();
                contactsChatRoomsDao.insert(data[0]);
                return null;
            }
        }.execute(data);
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


    public ArrayList<ContactsChatRooms> getAll() throws InterruptedException, ExecutionException {
         List<ContactsChatRooms> data = new AsyncTask<Void, Void, List<ContactsChatRooms>>() {
            @Override
            protected List<ContactsChatRooms> doInBackground(Void ... data) {
                ArrayList<ContactsChatRooms> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + contactsChatRoomsDao.getAll().toString());
                List<ContactsChatRooms> contactsChatRooms = contactsChatRoomsDao.getAll();

                for(int i = 0; i < contactsChatRooms.size(); i++) {
                    displayList.add(contactsChatRooms.get(i));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<ContactsChatRooms> contactsChatRooms) {
                super.onPostExecute(contactsChatRooms);
            }
        }.execute().get();
        return new ArrayList<>(data);
    }

    public ArrayList<ContactsChatRooms> getByContactId(int contactId) throws InterruptedException, ExecutionException {
        List<ContactsChatRooms> data = new AsyncTask<Integer, Void, List<ContactsChatRooms>>() {
            @Override
            protected List<ContactsChatRooms> doInBackground(Integer ... data) {
                ArrayList<ContactsChatRooms> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                int contactId = data[0];
                List<ContactsChatRooms> contactsChatRooms = contactsChatRoomsDao.getByContactsID(contactId);

                for(int i = 0; i < contactsChatRooms.size(); i++) {
                    displayList.add(contactsChatRooms.get(i));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<ContactsChatRooms> contactsChatRooms) {
                super.onPostExecute(contactsChatRooms);
            }
        }.execute(contactId).get();
        return new ArrayList<>(data);
    }


    public ArrayList<ContactsChatRooms> getByChatRoomId(final int chatRoomId) {
        List<ContactsChatRooms> data = null;
        try {
            data = new AsyncTask<Integer, Void, List<ContactsChatRooms>>() {
                @Override
                protected List<ContactsChatRooms> doInBackground(Integer ... data) {
                    ArrayList<ContactsChatRooms> displayList = new ArrayList<>();

                    AppDatabase db = App.getInstance().getDatabase();
                    ContactsChatRoomsDAO contactsChatRoomsDao = db.getContactsChatRoomsDao();

                    int chatRoomId = data[0];
                    Log.d("!!!!!LOG!!", "doInBackground: " + contactsChatRoomsDao.getAll().toString());
                    List<ContactsChatRooms> contactsChatRooms = contactsChatRoomsDao.getByChatRoomsID(chatRoomId);

                    for(int i = 0; i < contactsChatRooms.size(); i++) {
                        displayList.add(contactsChatRooms.get(i));
                    }
                    return displayList;
                }

                @Override
                protected void onPostExecute(List<ContactsChatRooms> contactsChatRooms) {
                    super.onPostExecute(contactsChatRooms);
                }
            }.execute(chatRoomId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(data);
    }



    public int getContactsNumber(int chatRoomId) {
        int data = 0;
        try {
            data = new AsyncTask<Integer, Void, Integer>() {
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
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return data;
    }
}

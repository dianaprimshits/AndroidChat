package com.bigsur.AndroidChatWithMaps.DBManager;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ChatRoomDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteChatRoomsManager implements StorageManager {

    @Override
    public void create(final DataFromDB data) {
        new AsyncTask<DataFromDB, Void, Void>() {
            @Override
            protected Void doInBackground(DataFromDB... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                chatRoomDAO.insert((ChatRooms) data[0].getData());
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
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                chatRoomDAO.update((ChatRooms) data[0].getData());
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
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                int id = data[0];
                chatRoomDAO.delete(id);
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
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                int id = data[0];
                ChatRooms c = chatRoomDAO.getByID(id);
                return new DataFromDB(c);
            }

            @Override
            protected void onPostExecute(DataFromDB chatRoom) {
                super.onPostExecute(chatRoom);
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
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + chatRoomDAO.getAll().toString());
                List<ChatRooms> chatRoomList = chatRoomDAO.getAll();

                for(int i = 0; i < chatRoomList.size(); i++) {
                    displayList.add(new DataFromDB(chatRoomList.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> chatRooms) {
                super.onPostExecute(chatRooms);
            }
        }.execute().get();
        return new ArrayList<>(data);
    }
}

package com.bigsur.AndroidChatWithMaps.DB.ChatRooms;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SQLiteChatRoomsManager {
    static SQLiteChatRoomsManager instance;

    private SQLiteChatRoomsManager() {
    }

    public static SQLiteChatRoomsManager getInstance() {
        if (instance == null) {
            instance = new SQLiteChatRoomsManager();
        }
        return instance;
    }


    public FutureTask<ChatRooms> taskGetContactById(int id) {
        FutureTask<ChatRooms> getContactById = new FutureTask<>(() -> {
            AppDatabase db = App.getInstance().getDatabase();
            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
            return chatRoomDAO.getByID(id);
        });
        return getContactById;
    }


    public FutureTask<ArrayList<ChatRooms>> taskGetAllChatRooms() {
        FutureTask<ArrayList<ChatRooms>> getAll = new FutureTask<>(new Callable<ArrayList<ChatRooms>>() {
            @Override
            public ArrayList<ChatRooms> call() {
                List<ChatRooms> data = null;
                try {
                    data = new AsyncTask<Void, Void, List<ChatRooms>>() {
                        @Override
                        protected ArrayList<ChatRooms> doInBackground(Void ... data) {
                            ArrayList<ChatRooms> displayList = new ArrayList<>();

                            AppDatabase db = App.getInstance().getDatabase();
                            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
                            Log.d("!!!!!LOG!!", "doInBackground: " + chatRoomDAO.getAll().toString());
                            List<ChatRooms> contactList = chatRoomDAO.getAll();
                            displayList.addAll(contactList);
                            return displayList;
                        }

                        @Override
                        protected void onPostExecute(List<ChatRooms> contacts) {
                            super.onPostExecute(contacts);
                        }
                    }.execute().get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return new ArrayList(data);
            }
        });
        return getAll;
    }


    public FutureTask<Void> taskCreateChatRoom(ChatRooms contact) {
        FutureTask<Void> createContact = new FutureTask<>(() -> {
            AppDatabase db = App.getInstance().getDatabase();
            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
            chatRoomDAO.insert(contact);
            return null;
        });
        return createContact;
    }


    public FutureTask<Void> taskUpdateContact(ChatRooms chat) {
        FutureTask<Void> updateContact = new FutureTask<>(() -> {
            AppDatabase db = App.getInstance().getDatabase();
            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
            chatRoomDAO.update(chat);
            return null;
        });
        return updateContact;
    }


    public FutureTask<Void> taskDeleteContactById(int id) {
        FutureTask<Void> deleteContactById = new FutureTask<>(() -> {
            AppDatabase db = App.getInstance().getDatabase();
            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
            chatRoomDAO.delete(id);
            return null;
        });
        return deleteContactById;
    }


    public FutureTask<ChatRooms> taskGetLastChatRoom() {
        FutureTask<ChatRooms> getLastContact = new FutureTask<>(() -> {
            AppDatabase db = App.getInstance().getDatabase();
            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
            return chatRoomDAO.getLastChatRoom();
        });
        return getLastContact;
    }

    public int getLastId() throws ExecutionException, InterruptedException {
        Integer lastId = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                AppDatabase db = App.getInstance().getDatabase();
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                Integer lastId = chatRoomDAO.getLastId();

                return lastId;
            }

            @Override
            protected void onPostExecute(Integer lastId) {
                super.onPostExecute(lastId);
            }
        }.execute().get();
        return lastId;
    }
}

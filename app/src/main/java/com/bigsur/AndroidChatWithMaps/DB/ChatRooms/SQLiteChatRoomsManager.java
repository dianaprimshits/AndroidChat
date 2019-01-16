package com.bigsur.AndroidChatWithMaps.DB.ChatRooms;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.ArrayList;
import java.util.List;
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


    public FutureTask<Void> create(ChatRooms chatRooms) {
        FutureTask<Void> createChat = new FutureTask<>(() -> {
            AppDatabase db = App.getInstance().getDatabase();
            ChatRoomDAO chatRoomDAO = db.getChatRoomDao();
            chatRoomDAO.insert(chatRooms);
            return null;
        });
        return createChat;
    }


    public void update(final DataWithIcon data) {
        new AsyncTask<DataWithIcon, Void, Void>() {
            @Override
            protected Void doInBackground(DataWithIcon... data) {
                AppDatabase db = App.getInstance().getDatabase();
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                chatRoomDAO.update((ChatRooms) data[0]);
                return null;
            }
        }.execute(data);
    }


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


    public DataWithIcon getById(final int id) {
        DataWithIcon data = null;
        try {
            data = new AsyncTask<Integer, Void, DataWithIcon>() {
                @Override
                protected DataWithIcon doInBackground(Integer... data) {
                    AppDatabase db = App.getInstance().getDatabase();
                    ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                    int id = data[0];
                    ChatRooms c = chatRoomDAO.getByID(id);
                    return c;
                }

                @Override
                protected void onPostExecute(DataWithIcon chatRoom) {
                    super.onPostExecute(chatRoom);
                }
            }.execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return data;
    }


    public ArrayList<DataWithIcon> getAll() {
        List<DataWithIcon> data = null;
        try {
            data = new AsyncTask<Void, Void, List<DataWithIcon>>() {
                @Override
                protected List<DataWithIcon> doInBackground(Void ... data) {
                    ArrayList<DataWithIcon> displayList = new ArrayList<>();

                    AppDatabase db = App.getInstance().getDatabase();
                    ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                    Log.d("!!!!!LOG!!", "doInBackground: " + chatRoomDAO.getAll().toString());
                    List<ChatRooms> chatRoomList = chatRoomDAO.getAll();

                    for(int i = 0; i < chatRoomList.size(); i++) {
                        displayList.add(chatRoomList.get(i));
                    }
                    return displayList;
                }

                @Override
                protected void onPostExecute(List<DataWithIcon> chatRooms) {
                    super.onPostExecute(chatRooms);
                }
            }.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        ArrayList arrayList = new ArrayList(data);
        return arrayList;
    }

   /* @Override
    public ArrayList<DataWithIcon> getSimilarData(String search) throws InterruptedException, ExecutionException {
        List<DataWithIcon> data = new AsyncTask<String, Void, List<DataWithIcon>>() {
            @Override
            protected List<DataWithIcon> doInBackground(String ... data) {
                ArrayList<DataWithIcon> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                ChatRoomDAO chatRoomDAO = db.getChatRoomDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + chatRoomDAO.getSimilarChatRooms(data[0]));
                List<ChatRooms> chatRoomList = chatRoomDAO.getSimilarChatRooms(data[0]);

                for(int i = 0; i < chatRoomList.size(); i++) {
                    displayList.add(new DataWithIcon(chatRoomList.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataWithIcon> chatRooms) {
                super.onPostExecute(chatRooms);
            }
        }.execute(search).get();
        return new ArrayList<>(data);
    }*/

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

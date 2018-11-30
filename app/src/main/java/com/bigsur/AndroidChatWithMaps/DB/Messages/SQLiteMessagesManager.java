package com.bigsur.AndroidChatWithMaps.DB.Messages;

import android.os.AsyncTask;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DB.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DB.StorageManager;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteMessagesManager implements StorageManager {
    static SQLiteMessagesManager instance;

    private SQLiteMessagesManager() {
    }


    public static SQLiteMessagesManager getInstance() {
        if (instance == null) {
            instance = new SQLiteMessagesManager();
        }
        return instance;
    }

    @Override
    public void create(final DataFromDB data) {
        new AsyncTask<DataFromDB, Void, Void>() {
            @Override
            protected Void doInBackground(DataFromDB... data) {
                AppDatabase db = App.getInstance().getDatabase();
                MessagesDAO messagesDAO = db.getMessagesDao();

                messagesDAO.insert((Messages) data[0].getData());
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
                MessagesDAO messagesDAO = db.getMessagesDao();

                messagesDAO.update((Messages) data[0].getData());
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
                MessagesDAO messagesDAO = db.getMessagesDao();

                int id = data[0];
                messagesDAO.delete(id);
                return null;
            }
        }.execute(id);
    }


    @Override
    public DataFromDB getById(final int id) throws ExecutionException, InterruptedException {
        return null;
    }


    @Override
    public ArrayList<DataFromDB> getAll() throws InterruptedException, ExecutionException {
        List<DataFromDB> data = new AsyncTask<Void, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Void... data) {
                ArrayList<DataFromDB> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                MessagesDAO messagesDAO = db.getMessagesDao();
                List<Messages> messagesList = messagesDAO.getAll();

                for (int i = 0; i < messagesList.size(); i++) {
                    displayList.add(new DataFromDB(messagesList.get(i)));
                }
                return displayList;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> messages) {
                super.onPostExecute(messages);
            }
        }.execute().get();
        return new ArrayList<>(data);
    }

    public List<DataFromDB> getByChatRoomId(int chatRoomId) throws ExecutionException, InterruptedException {
        List<DataFromDB> data = new AsyncTask<Integer, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Integer... params) {
                ArrayList<DataFromDB> result = new ArrayList<DataFromDB>();
                AppDatabase db = App.getInstance().getDatabase();
                MessagesDAO messagesDAO = db.getMessagesDao();
                List<Messages> messagesList = messagesDAO.getByChatRoomId(params[0]);

                for (int i = 0; i < messagesList.size(); i++) {
                    result.add(new DataFromDB(messagesList.get(i)));
                }
                return result;
            }

            @Override
            protected void onPostExecute(List<DataFromDB> messages) {
                super.onPostExecute(messages);
            }
        }.execute(chatRoomId).get();
        return new ArrayList<>(data);
    }

    @Override
    public List<DataFromDB> getSimilarData(String search) throws ExecutionException, InterruptedException {
        //do nothing
        return null;
    }

    public Messages getLastMessage(int chatId) {
        Messages data = null;
        try {
            data = new AsyncTask<Integer, Void, Messages>() {
                @Override
                protected Messages doInBackground(Integer... params) {
                    ArrayList<DataFromDB> result = new ArrayList<>();
                    AppDatabase db = App.getInstance().getDatabase();
                    MessagesDAO messagesDAO = db.getMessagesDao();
                    Messages message = messagesDAO.getLastMessage(params[0]);
                    return message;
                }

                @Override
                protected void onPostExecute(Messages message) {
                    super.onPostExecute(message);
                }
            }.execute(chatId).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return data;
    }
}

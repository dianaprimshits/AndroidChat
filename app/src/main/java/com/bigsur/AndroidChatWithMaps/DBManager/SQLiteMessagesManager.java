package com.bigsur.AndroidChatWithMaps.DBManager;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.MessagesDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SQLiteMessagesManager implements StorageManager {

    @SuppressLint("StaticFieldLeak")
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


    @SuppressLint("StaticFieldLeak")
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


    @SuppressLint("StaticFieldLeak")
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
        @SuppressLint("StaticFieldLeak") List<DataFromDB> data = new AsyncTask<Void, Void, List<DataFromDB>>() {
            @Override
            protected List<DataFromDB> doInBackground(Void ... data) {
                ArrayList<DataFromDB> displayList = new ArrayList<>();

                AppDatabase db = App.getInstance().getDatabase();
                MessagesDAO messagesDAO = db.getMessagesDao();

                Log.d("!!!!!LOG!!", "doInBackground: " + messagesDAO.getAll().toString());
                List<Messages> messagesList = messagesDAO.getAll();

                for(int i = 0; i < messagesList.size(); i++) {
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

    @Override
    public List<DataFromDB> getSimilarData(String search) throws ExecutionException, InterruptedException {
        //do nothing
        return null;
    }
}

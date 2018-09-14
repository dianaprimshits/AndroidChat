package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ChatRoomDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.MessagesDAO;

import static android.content.ContentValues.TAG;

public class Create extends AsyncTask<DataFromDB, Void, Void> {

    @Override
    protected Void doInBackground(DataFromDB... params) {
        AppDatabase db = App.getInstance().getDatabase();
        Log.d(TAG, "doInBackground: " + db.getDao(params[0].getData()));
        if (params[0].getData() instanceof Contacts) {
            ContactsDAO contactsDao = db.getContactsDao();
            contactsDao.insert((Contacts) params[0].getData());
        }

        if (params[0].getData() instanceof ChatRooms) {
            ChatRoomDAO chatRoomDao = db.getChatRoomDao();
            chatRoomDao.insert((ChatRooms) params[0].getData());
        }

        if (params[0].getData() instanceof Messages) {
            MessagesDAO messagesDao = db.getMessagesDao();
            messagesDao.insert((Messages) params[0].getData());
        }

        return null;
    }
}
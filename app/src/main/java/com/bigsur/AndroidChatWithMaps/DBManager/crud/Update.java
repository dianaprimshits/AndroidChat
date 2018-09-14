package com.bigsur.AndroidChatWithMaps.DBManager.crud;


import android.os.AsyncTask;

import com.bigsur.AndroidChatWithMaps.App;
import com.bigsur.AndroidChatWithMaps.DBManager.AppDatabase;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ChatRoomDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.ContactsDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DAO.MessagesDAO;
import com.bigsur.AndroidChatWithMaps.DBManager.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;

public class Update extends AsyncTask<DataFromDB, Void, Void>{

    @Override
    protected Void doInBackground(DataFromDB... params) {
        AppDatabase db = App.getInstance().getDatabase();

        if (params[0].getData() instanceof Contacts) {
            ContactsDAO contactsDao = db.getContactsDao();
            contactsDao.update((Contacts) params[0].getData());
        }

        if (params[0].getData() instanceof ChatRooms) {
            ChatRoomDAO chatRoomDao = db.getChatRoomDao();
            chatRoomDao.update((ChatRooms) params[0].getData());
        }

        if (params[0].getData() instanceof Messages) {
            MessagesDAO messagesDao = db.getMessagesDao();
            messagesDao.update((Messages) params[0].getData());
        }

        return null;
    }
}

package com.bigsur.AndroidChatWithMaps;


import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.Contacts.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.jsonserver.JsonServerManager;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ContactsDataManager extends DataWithIconManager {

    @Override
    public void create(DataWithIcon data) throws ExecutionException, InterruptedException {

    }

    @Override
    public void update(DataWithIcon data) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public DataWithIcon getById(int id) {
        return null;
    }

    public ArrayList<DataWithIcon> getAll() {

        JsonServerManager retrofitManager = new JsonServerManager();
        SQLiteContactsManager sqLiteContactsManager = SQLiteContactsManager.getInstance();

        ArrayList<DataWithIcon> resultList = new ArrayList<>();

        try {
            FutureTask<ArrayList<Contacts>> serverTask = retrofitManager.getContacts();
            new Thread(serverTask).start();
            resultList.addAll(serverTask.get());

            if(resultList.size() == 0) {
                FutureTask<ArrayList<Contacts>> localSQLiteTask = sqLiteContactsManager.taskGetForAll();
                new Thread(localSQLiteTask).start();
                resultList.addAll(localSQLiteTask.get());
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return resultList;
    }
}

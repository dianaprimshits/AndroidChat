package com.bigsur.AndroidChatWithMaps.Domain.ViewableContact;


import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.Contacts.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.Server.ServerManager.JsonContactsManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

public class ViewableContactManager extends DataWithIconManager {
    SQLiteContactsManager contactsManager;
    static ViewableContactManager instance;
    private String TAG = "!!!LOG!!!";


    private ViewableContactManager(SQLiteContactsManager manager) {
        this.contactsManager = manager;
    }


    public static ViewableContactManager getInstance() {
        if (instance == null) {
            instance = new ViewableContactManager(SQLiteContactsManager.getInstance());
        }
        return instance;
    }


    @Override
    public void create(DataWithIcon data, Runnable onSuccess, Runnable onFail) {
        //побереги глаза
        Runnable runnable = () -> {
            JsonContactsManager serverManager = JsonContactsManager.getInstance();
            SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();
            FutureTask<HashMap> createServerContact = serverManager.create(((ViewableContact)data).getContact());
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(createServerContact).start();

            try {
                if(!(Boolean)createServerContact.get().values().toArray()[0]) {
                    handler.post(onFail);
                } else {
                    FutureTask createSqlContact = sqlManager.taskCreateContact((Contacts) createServerContact.get().keySet().toArray()[0]);
                    new Thread(createSqlContact).start();
                    handler.post(onSuccess);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public void update(DataWithIcon data, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            JsonContactsManager serverManager = JsonContactsManager.getInstance();
            SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();
            FutureTask updateSql = sqlManager.taskUpdateContact(((ViewableContact)data).getContact());
            FutureTask<Boolean> updateServer = serverManager.update(((ViewableContact) data).getContact());
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(updateServer).start();

            try {
                if(!updateServer.get()) {
                    handler.post(onFail);
                } else {
                    new Thread(updateSql).start();
                    handler.post(onSuccess);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        new Thread(runnable).start();

    }

    @Override
    public void delete(int id, Runnable onSuccess, Runnable onFail) {
        Runnable runnable = () -> {
            JsonContactsManager serverManager = JsonContactsManager.getInstance();
            SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();

            FutureTask deleteFromSql = sqlManager.taskDeleteContactById(id);
            FutureTask<Boolean> deleteFromServer = serverManager.delete(id);
            Handler handler = new Handler(Looper.getMainLooper());
            new Thread(deleteFromServer).start();

            try {
                if (!deleteFromServer.get()) {
                    handler.post(onFail);
                } else {
                    new Thread(deleteFromSql).start();
                    handler.post(onSuccess);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            dataUpdated();
        };
        new Thread(runnable).start();
    }


    @Override
    public DataWithIcon getById(int id) {
        SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();
        FutureTask<Contacts> getContact = sqlManager.taskGetContactById(id);
        DataWithIcon contact = null;
        new Thread(getContact).start();
        try {
            contact = new ViewableContact(getContact.get());
        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, " ViewableContactManager getContactById from SQLite: " + e);
        }
        return contact;
    }


    @Override
    public ArrayList<DataWithIcon> getAll() {
        JsonContactsManager serverManager = JsonContactsManager.getInstance();
        SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();

        ArrayList<DataWithIcon> contactListFromServer = new ArrayList<>();
        ArrayList<DataWithIcon> contactListFromSQLiteDB = new ArrayList<>();

        try {
            FutureTask<ArrayList<Contacts>> sqliteTask = sqlManager.taskGetAllContacts();
            new Thread(sqliteTask).start();
            ArrayList<Contacts> ar = sqliteTask.get();
            for (Contacts contact : ar) {
                contactListFromSQLiteDB.add(new ViewableContact(contact));
            }

        } catch (InterruptedException | ExecutionException e) {
            Log.d(TAG, " ViewableContactManager getAll from SQLiteContactsManager: " + e);
        }


        Runnable copyNewContactFromServer = () -> {
            try {
                FutureTask<ArrayList<Contacts>> serverTask = serverManager.getAll();
                new Thread(serverTask).start();
                contactListFromServer.addAll(serverTask.get().stream()
                        .map(contact -> (DataWithIcon) (new ViewableContact(contact)))
                        .collect(Collectors.toList()));
            } catch (ExecutionException | InterruptedException e) {
                Log.d(TAG, " ViewableContactManager getAll from JsonContactsManager: " + e);
            }

            if (contactListFromServer.size() > contactListFromSQLiteDB.size()) {
                List<Contacts> contactsToCreateInSQLite = contactListFromServer.stream()
                        .filter(i -> !(contactListFromSQLiteDB.contains(i)))
                        .map(q -> ((ViewableContact) q).getContact())
                        .collect(Collectors.toList());

                for (Contacts contact : contactsToCreateInSQLite) {
                    FutureTask postNewContacts = sqlManager.taskCreateContact(contact);
                    new Thread(postNewContacts).start();
                }
            }
        };
        new Thread(copyNewContactFromServer).start();
        return contactListFromSQLiteDB;
    }


    @Override
    public void addDataChangeListener(Object listener, Runnable callBack) {
         //addDataChangeListener(listener, callBack);
    }

    @Override
    public void removeDataChangeListener(Object listener) {
        //  removeDataChangeListener(listener);
    }
}

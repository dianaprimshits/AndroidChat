package com.bigsur.AndroidChatWithMaps.Domain.ViewableContact;


import android.util.Log;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.Contacts.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.jsonserver.ServerManager.JsonContactsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
    public void create(DataWithIcon data) {

    }

    @Override
    public void update(DataWithIcon data) {
        JsonContactsManager serverManager = JsonContactsManager.getInstance();
        SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();

        FutureTask updateSql = sqlManager.taskUpdateContact((Contacts) data);
        FutureTask updateServer = serverManager.update(((ViewableContact)data).getContact());
    }

    @Override
    public void delete(int id) {
        JsonContactsManager serverManager = JsonContactsManager.getInstance();
        SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();

        FutureTask deleteFromSql = sqlManager.taskDeleteContactById(id);
        FutureTask<Boolean> deleteFromServer = serverManager.delete(id);

        new Thread(deleteFromSql).start();


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                new Thread(deleteFromServer).start();

                try {
                    if (deleteFromServer.get()) {
                        cancel();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.schedule(timerTask, 0, 20000);
        dataUpdated();
    }


    @Override
    public DataWithIcon getById(int id) {
        SQLiteContactsManager sqlManager = SQLiteContactsManager.getInstance();
        FutureTask<Contacts> getContact = sqlManager.taskGetContactById(id);
        DataWithIcon contact = null;
        new Thread(getContact).start();
        try {
            contact = (DataWithIcon) getContact.get();
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



        Runnable copyNewContactFromServer = new Runnable() {
            @Override
            public void run() {
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
                            .map(q -> ((ViewableContact)q).getContact())
                            .collect(Collectors.toList());

                    for (Contacts contact : contactsToCreateInSQLite) {
                        FutureTask postNewContacts = sqlManager.taskCreateContact(contact);
                        new Thread(postNewContacts).start();
                    }
                } else if (contactListFromServer.size() < contactListFromSQLiteDB.size()) {

                    List<Contacts> contactsToDeleteFromSQLite = contactListFromSQLiteDB.stream()
                            .filter(i -> !(contactListFromServer.contains(i)))
                            .map(q -> ((ViewableContact) q).getContact())
                            .collect(Collectors.toList());

                    for (Contacts contact : contactsToDeleteFromSQLite) {
                        FutureTask deleteContact = sqlManager.taskDeleteContactById(contact.getId());
                        new Thread(deleteContact).start();
                    }
                }
            }
        };

        copyNewContactFromServer.run();


        return contactListFromSQLiteDB;
    }


    @Override
    public void addDataChangeListener(Object listener, Runnable callBack) {
        // addDataChangeListener(listener, callBack);
    }

    @Override
    public void removeDataChangeListener(Object listener) {
        //  removeDataChangeListener(listener);
    }
}

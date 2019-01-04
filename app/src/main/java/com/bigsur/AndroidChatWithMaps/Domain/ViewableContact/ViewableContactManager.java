package com.bigsur.AndroidChatWithMaps.Domain.ViewableContact;


import com.bigsur.AndroidChatWithMaps.DB.Contacts.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.ArrayList;

public class ViewableContactManager extends DataWithIconManager {
    SQLiteContactsManager contactsManager;
    static ViewableContactManager instance;

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
        contactsManager.create(data);
    }

    @Override
    public void update(DataWithIcon data) {
        contactsManager.update(data);
    }

    @Override
    public void delete(int id) {
        contactsManager.delete(id);
    }

    @Override
    public DataWithIcon getById(int id) {
        return contactsManager.getById(id);
    }

    @Override
    public ArrayList<DataWithIcon> getAll() {
       return contactsManager.getAll();
    }

    @Override
    public void addDataChangeListener(Object listener, Runnable callBack) {
        contactsManager.addDataChangeListener(listener, callBack);
    }

    @Override
    public void  removeDataChangeListener(Object listener) {
        contactsManager.removeDataChangeListener(listener);
    }
}

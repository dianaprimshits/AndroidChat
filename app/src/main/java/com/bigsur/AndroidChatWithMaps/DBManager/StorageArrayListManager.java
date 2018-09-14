package com.bigsur.AndroidChatWithMaps.DBManager;

import java.util.ArrayList;
/*
public class StorageArrayListManager implements StorageManager {
    private static ArrayList<Contacts> contacts;
    private static StorageArrayListManager instance;

    private StorageArrayListManager() {
        contacts = new ArrayList<Contacts>();
    }

    public static StorageArrayListManager getInstance() {
        if (instance == null) {
            instance = new StorageArrayListManager();
        }
        return instance;
    }

    @Override
    public void create(Contacts contact) {
        int freeId = contacts.size() + 1;
        Contacts contactToSave = new Contacts(freeId, contact.getName(), contact.getPhone_number());
        contacts.add(contactToSave);
    }

    @Override
    public void update(Contacts contact) {
        int contactToUpgradeId = contact.getId();
        for(int i = 0; i < contacts.size(); i++) {
            if(contactToUpgradeId == contacts.get(i).getId()) {
                contacts.set(i, contact);
            }
        }
    }

    @Override
    public void delete(int id) {
        for(int i = 0; i < contacts.size(); i++) {
            if(id == contacts.get(i).getId()) {
                contacts.remove(i);
            }
        }
    }

    @Override
    public Contacts getById(int id) {
        for(int i = 0; i < contacts.size(); i++) {
            int contactToUpgradeId = contacts.get(i).getId();
            if(id == contactToUpgradeId) {
                return contacts.get(i);
            }
        }
       return null;
    }

    @Override
    public ArrayList<Contacts> getAll() {
        return contacts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < contacts.size(); i++) {
            sb.append(contacts.get(i));
        }
        return sb.toString();
    }

    //public CustomAdapterForContacts getAdapter(Context context) {
    //
    // return new ArrayAdapter<Contacts>(context, R.layout.listview, contacts);
    //    return new CustomAdapterForContacts(context, contacts);
    //}

}
*/
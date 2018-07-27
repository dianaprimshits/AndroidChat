package com.bigsur.help29062018.DBManager;


import android.database.sqlite.SQLiteDatabase;

public class ContactsAction {


    public int addContact(SQLiteDatabase db, Contact contact) {

        return contact.getId();
    }

    public void deleteContact (SQLiteDatabase db, int id) {

    }
}

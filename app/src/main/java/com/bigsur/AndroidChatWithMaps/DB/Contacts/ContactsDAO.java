package com.bigsur.AndroidChatWithMaps.DB.Contacts;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contacts contact);

    @Update
    void update(Contacts contact);

    @Query("DELETE FROM contacts WHERE _id = :id")
    void delete(int id);

    @Query("SELECT * FROM contacts")
    List<Contacts> getAll();

    @Query("SELECT * FROM contacts WHERE _id = :id")
    Contacts getByID(int id);

    @Query("SELECT * FROM contacts WHERE contact_name LIKE :search OR phone_number LIKE :search")
    List<Contacts> getSimilarContacts(String search);

    @Query("SELECT * FROM contacts WHERE _id = (SELECT MAX(_id) FROM contacts)")
    Contacts getLastContact();
}

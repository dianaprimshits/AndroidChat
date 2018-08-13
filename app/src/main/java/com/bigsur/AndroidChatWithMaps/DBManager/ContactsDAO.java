package com.bigsur.AndroidChatWithMaps.DBManager;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactsDAO {
    @Insert
    void insert(Contacts contact);

    @Update
    void update(Contacts contact);

    @Delete
    void delete(Contacts contact);

    @Query("SELECT * FROM contacts")
    List<Contacts> getAll();

    @Query("SELECT * FROM contacts WHERE id = :id")
    Contacts getByID(int id);
}

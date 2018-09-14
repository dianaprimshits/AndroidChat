package com.bigsur.AndroidChatWithMaps.DBManager.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;

import java.util.List;

@Dao
public interface ContactsDAO extends TheUDaoInterface {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contacts contact);

    @Update
    void update(Contacts contact);

    @Query("delete from contacts where _id = :id")
    void delete(int id);

    @Query("SELECT * FROM contacts")
    List<Contacts> getAll();

    @Query("SELECT * FROM contacts WHERE _id = :id")
    Contacts getByID(int id);

}

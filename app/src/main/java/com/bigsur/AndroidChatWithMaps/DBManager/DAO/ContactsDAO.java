package com.bigsur.AndroidChatWithMaps.DBManager.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;

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

}

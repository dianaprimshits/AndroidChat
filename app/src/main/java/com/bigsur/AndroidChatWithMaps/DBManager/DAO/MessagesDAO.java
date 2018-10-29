package com.bigsur.AndroidChatWithMaps.DBManager.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;

import java.util.List;

@Dao
public interface MessagesDAO {
    @Insert
    void insert(Messages message);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Messages message);

    @Query("DELETE FROM messages WHERE message_id = :id")
    void delete(int id);

    @Query("SELECT * FROM messages")
    List<Messages> getAll();

}

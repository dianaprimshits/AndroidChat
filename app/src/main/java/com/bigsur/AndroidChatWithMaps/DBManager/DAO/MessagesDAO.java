package com.bigsur.AndroidChatWithMaps.DBManager.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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

    @Query("SELECT * FROM messages WHERE message_to = :chatRoomId")
    List<Messages> getByChatRoomId(int chatRoomId);

}

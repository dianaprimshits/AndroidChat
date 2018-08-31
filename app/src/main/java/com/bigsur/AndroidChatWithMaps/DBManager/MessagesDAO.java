package com.bigsur.AndroidChatWithMaps.DBManager;

/*
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MessagesDAO {
    @Insert
    void addMessage(Messages message);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMessage(Messages message);

    @Query("delete from message where id = :id")
    void delete(int id);

    @Query("SELECT * FROM messages WHERE contactId = :contactId")
    List<Messages> getMessagesForContact(int contactId);

}
*/
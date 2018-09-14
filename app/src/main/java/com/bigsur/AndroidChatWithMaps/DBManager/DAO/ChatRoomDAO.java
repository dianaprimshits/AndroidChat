package com.bigsur.AndroidChatWithMaps.DBManager.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;

import java.util.List;

@Dao
public interface ChatRoomDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChatRooms chatRoom);

    @Update
    void update(ChatRooms chatRoom);

    @Query("DELETE FROM chat_rooms where chat_room_id = :chatRoomId")
    void delete(int chatRoomId);

    @Query("SELECT * FROM chat_rooms")
    List<ChatRooms> getAll();

    @Query("SELECT * FROM chat_rooms WHERE chat_room_id = :chatRoomId")
    ChatRooms getByID(int chatRoomId);
}

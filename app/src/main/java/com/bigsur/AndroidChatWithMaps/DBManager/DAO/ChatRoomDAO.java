package com.bigsur.AndroidChatWithMaps.DBManager.DAO;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM chat_rooms WHERE chat_room_name LIKE :search")
    List<ChatRooms> getSimilarChatRooms(String search);
}

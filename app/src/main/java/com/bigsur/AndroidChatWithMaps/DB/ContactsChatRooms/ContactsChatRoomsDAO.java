package com.bigsur.AndroidChatWithMaps.DB.ContactsChatRooms;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactsChatRoomsDAO {
    @Insert
    void insert(ContactsChatRooms contactsChatRooms);

    @Query("DELETE FROM connections WHERE contact_id = :contactsId AND chat_id = :chatRoomsId")
    void delete(int contactsId, int chatRoomsId);

    @Query("DELETE FROM connections WHERE contact_id = :contactsId")
    void deleteByContactsId(int contactsId);

    @Query("DELETE FROM connections WHERE chat_id = :chatRoomsId")
    void deleteByChatRoomsId(int chatRoomsId);

    @Query("SELECT * FROM connections")
    List<ContactsChatRooms> getAll();

    @Query("SELECT * FROM connections WHERE contact_id = :id")
    List<ContactsChatRooms> getByContactsID(int id);

    @Query("SELECT * FROM connections WHERE chat_id = :id")
    List<ContactsChatRooms> getByChatRoomsID(int id);

    @Query("SELECT COUNT(contact_id) FROM connections WHERE chat_id = :chatRoomId")
    int getContactsNumber(int chatRoomId);
}

package com.bigsur.AndroidChatWithMaps.DBManager.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ContactsChatRooms;

import java.util.List;

@Dao
public interface ContactsChatRoomsDAO {
    @Insert
    void insert(ContactsChatRooms contactsChatRooms);

    @Query("DELETE FROM groups WHERE contact_id = :contactsId AND chat_id = :chatRoomsId")
    void delete(int contactsId, int chatRoomsId);

    @Query("DELETE FROM groups WHERE contact_id = :contactsId")
    void deleteByContactsId(int contactsId);

    @Query("DELETE FROM groups WHERE chat_id = :chatRoomsId")
    void deleteByChatRoomsId(int chatRoomsId);

    @Query("SELECT * FROM groups")
    List<ContactsChatRooms> getAll();

    @Query("SELECT * FROM groups WHERE contact_id = :id")
    List<ContactsChatRooms> getByContactsID(int id);

    @Query("SELECT * FROM groups WHERE chat_id = :id")
    List<ContactsChatRooms> getByChatRoomsID(int id);
}

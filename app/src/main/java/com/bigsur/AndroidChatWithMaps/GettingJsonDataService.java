package com.bigsur.AndroidChatWithMaps;


import com.bigsur.AndroidChatWithMaps.DB.ChatRooms.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.Messages.Messages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GettingJsonDataService {


    //contacts
    @GET("contacts")
    Call<List<Contacts>> getContacts();


    //chats
    @GET("chat_rooms")
    Call<List<ChatRooms>> getChatRooms();


    //messages
    @GET("messages")
    Call<List<Messages>> getMessages();
}

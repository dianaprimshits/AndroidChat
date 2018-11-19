package com.bigsur.AndroidChatWithMaps;


import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationInstance;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GettingJsonDataService {

    //login
    @GET("/login")
    Call<AuthenticationInstance> getLoginInstance();

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

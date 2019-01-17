package com.bigsur.AndroidChatWithMaps.Server.api;


import com.bigsur.AndroidChatWithMaps.Server.entities.JsonChatRoom;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatRoomApi {

    @GET("/chat_rooms")
    Call<ArrayList<JsonChatRoom>> getAll();

    @FormUrlEncoded
    @POST("/chat_rooms")
    Call<JsonChatRoom> postChatRoom(JsonChatRoom chatRoom);

    @DELETE("/chat_rooms/{id}")
    Call<Void> deleteChatRoom(@Path("id") int id);

    @PATCH("/chat_rooms/{id}")
    Call<Void> updateChatRoom(@Path("id") int id, @Body JsonChatRoom chatroom);
}

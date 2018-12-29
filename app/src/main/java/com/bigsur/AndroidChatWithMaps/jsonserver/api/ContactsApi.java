package com.bigsur.AndroidChatWithMaps.jsonserver.api;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ContactsApi {
    @GET("/contacts")
    Call<ArrayList<Contacts>> getAll();

    @FormUrlEncoded
    @POST("/contacts")
    Call<Contacts> postContact(
            @Field("id") int id,
            @Field("name") String title,
            @Field("phone") String subTitle,
            @Field("avatar") String avatar);
}

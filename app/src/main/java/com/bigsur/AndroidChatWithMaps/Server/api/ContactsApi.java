package com.bigsur.AndroidChatWithMaps.Server.api;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @DELETE("/contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);

    @PATCH("/contacts/{id}")
    Call<Void> updateContact(@Path("id") int id, @Body Contacts contacts);
}

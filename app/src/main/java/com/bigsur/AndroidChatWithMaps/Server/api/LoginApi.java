package com.bigsur.AndroidChatWithMaps.Server.api;

import com.bigsur.AndroidChatWithMaps.Server.entities.AuthenticationInstance;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoginApi {
    @GET("/login")
    Call<AuthenticationInstance> getLoginInstance();


}

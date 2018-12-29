package com.bigsur.AndroidChatWithMaps.jsonserver.api;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationInstance;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoginApi {
    @GET("/login")
    Call<AuthenticationInstance> getLoginInstance();


}

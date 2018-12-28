package com.bigsur.AndroidChatWithMaps;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LoginApi {
    @GET("/login")
    Call<List<AuthenticationInstance>> getLoginInstances();

}

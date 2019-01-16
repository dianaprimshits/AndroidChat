package com.bigsur.AndroidChatWithMaps.Server;


import com.google.gson.annotations.SerializedName;

public class AuthenticationInstance {
    @SerializedName("user_id")
    private int userId;
    private boolean success;


    public AuthenticationInstance(int id, boolean success) {
        this.success = success;
        this.userId = id;
    }

    public AuthenticationInstance getAuthenticationInstance() {
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isSuccess() {
        return success;
    }
}

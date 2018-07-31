package com.bigsur.AndroidChatWithMaps.AuthManager;


public interface AuthManager {
    void setupStore();
    Credentials reloadData();
    void saveData(Credentials credentials);
}

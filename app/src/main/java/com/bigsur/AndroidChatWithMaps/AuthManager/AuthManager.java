package com.bigsur.AndroidChatWithMaps.AuthManager;


public interface AuthManager {
    void tryLoginWithSavedData(Runnable onSuccess, Runnable onFail);
    Credentials getSavedCredentials();
    void login(Credentials credentials, Runnable onSuccess, Runnable onFail);
}

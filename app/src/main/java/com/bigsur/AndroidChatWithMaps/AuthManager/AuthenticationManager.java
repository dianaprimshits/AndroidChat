package com.bigsur.AndroidChatWithMaps.AuthManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.security.KeyStore;
import java.util.List;

import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;


public class AuthenticationManager implements AuthManager {

    private static AuthenticationManager instance;
    private String TAG = "!!!LOG!!!";

    private AuthenticationManager() {
    }

    public static AuthenticationManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Must call init() before using the store");
        }
        return instance;
    }

    public static void init(Context context) {
        instance = new AuthenticationManager();
        try {
            String storeFileName = "securedStore";
            String keyPrefix = "vss";
            byte[] seedKey = "SecuredData".getBytes();
            SecuredPreferenceStore.init(context, storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());

            instance.setupStore();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("LOGLOGLOGLOG!!!11!!!", "init: " + SecuredPreferenceStore.getSharedInstance().getAll().toString());

    }


    public void setupStore() {
        SecuredPreferenceStore.setRecoveryHandler(new DefaultRecoveryHandler() {
            @Override
            protected boolean recover(Exception e, KeyStore keyStore, List<String> keyAliases, SharedPreferences preferences) {
                return super.recover(e, keyStore, keyAliases, preferences);
            }
        });
        try {
            getSavedCredentials();
        } catch (Exception e) {
            Log.e("!LOG!SECURED-PREFERENCE", "", e);
        }
    }

    public void saveUserId(int id) {
        String key = "ID";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putInt(key, id).apply();
        Log.d("!!!LOG!!!", String.format("id: %s", prefStore.getString(key, null)));
    }

    public int getUserId() {
        String key = "ID";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        Log.d("!!!LOG!!!", String.format("id: %s", prefStore.getInt(key, 0)));
        return prefStore.getInt(key, 0);
    }

    public Credentials getSavedCredentials() {
        Credentials credentials = null;
        String loginKey = "TEXT_LOGIN";
        String passKey = "TEXT_PASSWORD";

        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        String loginString = prefStore.getString(loginKey, null);
        String passString = prefStore.getString(passKey, null);

        if ((loginString != null) & (passString != null)) {
            credentials = new Credentials(loginString, passString);
        }
        Log.d("!!!LOG!!!", String.format("login %s, password %s", prefStore.getString(loginKey, null), prefStore.getString(passKey, null)));
        return credentials;
    }


    public void login(Credentials credentials, Runnable onSuccess, Runnable onFail) {
        if (credentials == null
                || credentials.getLogin().isEmpty()
                || credentials.getPassword().isEmpty()
                || credentials.getLogin() == null
                || credentials.getPassword() == null
                ) {
            onFail.run();
            return;
        }

        String loginKey = "TEXT_LOGIN";
        String passKey = "TEXT_PASSWORD";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        String loginString = credentials.getLogin();
        String passString = credentials.getPassword();
        prefStore.edit().putString(loginKey, loginString).apply();
        prefStore.edit().putString(passKey, passString).apply();
        Log.d("!!!LOG!!!", String.format("login %s, password %s", prefStore.getString(loginKey, null), prefStore.getString(passKey, null)));
        onSuccess.run();
    }


    public void changeLogin(String login) {
        String loginKey = "TEXT_LOGIN";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putString(loginKey, login).apply();
        Log.d("!!!LOG!!!", String.format("login %s, password", prefStore.getString(loginKey, null)));
    }

    public boolean loginValidationCheck(String login) {
        if (login == null || login.isEmpty()) {
            return false;
        }
        return true;
    }


    public void changeUsername(String username) {
        String key = "TEXT_USERNAME";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putString(key, username).apply();
        Log.d("!!!LOG!!!", String.format("username: %s", prefStore.getString(key, null)));

    }


    public String getUsername() {
        String key = "TEXT_USERNAME";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        return prefStore.getString(key, null);
    }


    public void changePhoneNumber(String phone) {
        String key = "TEXT_PHONE_NUMBER";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putString(key, phone).apply();
        Log.d("!!!LOG!!!", String.format("phone number: %s", prefStore.getString(key, null)));

    }


    public String getPhoneNumber() {
        String key = "TEXT_PHONE_NUMBER";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        Log.d("!!!LOG!!!", String.format("phone number: %s", prefStore.getString(key, null)));
        return prefStore.getString(key, null);
    }

    public void changeBio(String bio) {
        String key = "BIO";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putString(key, bio).apply();
        Log.d("!!!LOG!!!", String.format("bio: %s", prefStore.getString(key, null)));
    }


    public String getBio() {
        String key = "BIO";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        Log.d("!!!LOG!!!", String.format("bio: %s", prefStore.getString(key, null)));
        return prefStore.getString(key, null);
    }


    public void tryLoginWithSavedData(final Runnable onSuccess, final Runnable onFail) {
        login(getSavedCredentials(),
                new Runnable() {
                    @Override
                    public void run() {
                        onSuccess.run();
                    }
                }
                ,
                new Runnable() {
                    @Override
                    public void run() {
                        onFail.run();
                    }
                }
        );
    }
}

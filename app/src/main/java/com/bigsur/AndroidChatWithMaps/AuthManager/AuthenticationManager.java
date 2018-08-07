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

package com.bigsur.AndroidChatWithMaps.AuthManager;

import android.content.SharedPreferences;
import android.util.Log;

import java.security.KeyStore;
import java.util.List;

import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;


public class AuthenticationManager implements AuthManager {

    public void setupStore() {
        SecuredPreferenceStore.setRecoveryHandler(new DefaultRecoveryHandler() {
            @Override
            protected boolean recover(Exception e, KeyStore keyStore, List<String> keyAliases, SharedPreferences preferences) {
                return super.recover(e, keyStore, keyAliases, preferences);
            }
        });
        try {
            reloadData();
        } catch (Exception e) {
            Log.e("!LOG!SECURED-PREFERENCE", "", e);
        }
    }



   public Credentials reloadData() {
        String loginKey = "TEXT_LOGIN";
        String passKey = "TEXT_PASSWORD";

        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        Credentials credentials = new Credentials();
        String loginString = prefStore.getString(loginKey, null);
        String passString = prefStore.getString(passKey, null);

        if ((loginString != null) & (passString != null)) {
           credentials.setLogin(loginString);
           credentials.setPassword(passString);
        }
        Log.d("!!!LOG!!!", String.format("login %s, password %s", prefStore.getString(loginKey, null), prefStore.getString(passKey, null)));
        return credentials;
    }

    public void saveData(Credentials credentials) {
        String loginKey = "TEXT_LOGIN";
        String passKey = "TEXT_PASSWORD";
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        String loginString = credentials.getLogin() ;
        String passString = credentials.getPassword();
        prefStore.edit().putString(loginKey, loginString.length() > 0 ? loginString : null).apply();
        prefStore.edit().putString(passKey, passString.length() > 0 ? passString : null).apply();
        Log.d("!!!LOG!!!", String.format("login %s, password %s", prefStore.getString(loginKey, null), prefStore.getString(passKey, null)));
    }
}

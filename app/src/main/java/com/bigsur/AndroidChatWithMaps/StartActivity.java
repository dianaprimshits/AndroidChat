package com.bigsur.AndroidChatWithMaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.Home.MenuScreenActivity;

import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;


public class StartActivity extends AppCompatActivity {
    String loginKey = "TEXT_LOGIN";
    String passKey = "TEXT_PASSWORD";
    AuthManager authManager = new AuthenticationManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
        try {
            String storeFileName = "securedStore";
            String keyPrefix = "vss";
            byte[] seedKey = "SecuredData".getBytes();
            SecuredPreferenceStore.init(getApplicationContext(), storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());

            authManager.setupStore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int SPLASH_TIME_OUT = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(authManager.reloadData().getLogin() == null & authManager.reloadData().getPassword() == null) {
                    Intent loginIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Intent menuIntent = new Intent(StartActivity.this, MenuScreenActivity.class);
                    startActivity(menuIntent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}

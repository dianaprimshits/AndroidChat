package com.bigsur.AndroidChatWithMaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.Home.MenuScreenActivity;


public class StartActivity extends AppCompatActivity {
    AuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        AuthenticationManager.init(getApplicationContext());
        authManager = AuthenticationManager.getInstance();
        int SPLASH_TIME_OUT = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            authManager.tryLoginWithSavedData(
                new Runnable() {
                    @Override
                    public void run() {
                    Intent loginIntent = new Intent(StartActivity.this, MenuScreenActivity.class);
                    startActivity(loginIntent);
                    finish();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                    Intent menuIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(menuIntent);
                    finish();
                }
            });
            }
        }, SPLASH_TIME_OUT);
    }
}

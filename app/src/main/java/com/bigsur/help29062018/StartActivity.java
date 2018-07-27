package com.bigsur.help29062018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        int SPLASH_TIME_OUT = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                    Intent loginIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

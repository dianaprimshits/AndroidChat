package com.bigsur.help29062018;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;


public class StartActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 7000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        final File keyFile = new File("key.keystore");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(keyFile.exists() && !keyFile.isDirectory() /*keyFile.isFile() */) {
                    Intent loginIntent = new Intent(StartActivity.this, MenuScreenActivity.class);
                    startActivity(loginIntent);
                    finish();
                } else {
                    Intent loginIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}

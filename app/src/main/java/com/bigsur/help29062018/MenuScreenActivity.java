package com.bigsur.help29062018;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MenuScreenActivity extends AppCompatActivity implements View.OnClickListener {

    Button mapsButton;
    Button chatsButton;
    Button settingsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen_activity);

        mapsButton = (Button)findViewById(R.id.buttonMaps);
        mapsButton.setOnClickListener(this);
        chatsButton = (Button)findViewById(R.id.buttonChat);
        chatsButton.setOnClickListener(this);
        settingsButton = (Button)findViewById(R.id.buttonSettings);
        settingsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.buttonMaps:
               //Создаем переход
               intent = new Intent(MenuScreenActivity.this, MapsActivity.class);
               //Запускаем его при нажатии
               startActivity(intent);
               break;
            case R.id.buttonChat:
                intent = new Intent(MenuScreenActivity.this, ChatsActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonSettings:
                intent = new Intent(MenuScreenActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}

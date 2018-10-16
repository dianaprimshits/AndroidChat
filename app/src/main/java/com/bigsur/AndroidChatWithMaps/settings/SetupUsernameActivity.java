package com.bigsur.AndroidChatWithMaps.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.Home.MenuScreenActivity;
import com.bigsur.AndroidChatWithMaps.R;

public class SetupUsernameActivity extends AppCompatActivity {
    ImageButton backButton;
    ImageButton checkButton;
    EditText usernameED;
    final AuthenticationManager authManager = AuthenticationManager.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_username_in_settings_fr);

        backButton = (ImageButton) findViewById(R.id.settingsFrEditNameButtonBack);
        usernameED = (EditText) findViewById(R.id.settingsFrEditNameEV);
        checkButton = (ImageButton) findViewById(R.id.settingsFrEditNameButtonCheck);

        usernameED.setText(authManager.getUsername());

        View.OnClickListener onClickButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.settingsFrEditNameButtonBack:
                        onBackPressed();
                        break;
                    case R.id.settingsFrEditNameButtonCheck:
                        authManager.changeUsername(usernameED.getText().toString());
                        Intent intent = new Intent(SetupUsernameActivity.this, MenuScreenActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        backButton.setOnClickListener(onClickButton);
        checkButton.setOnClickListener(onClickButton);
    }

}

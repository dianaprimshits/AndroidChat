package com.bigsur.AndroidChatWithMaps.UI.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.UI.Home.MenuScreenActivity;
import com.bigsur.AndroidChatWithMaps.R;

public class SetupPhoneActivity extends AppCompatActivity implements View.OnClickListener {
    AuthenticationManager authManager = AuthenticationManager.getInstance();
    EditText phoneET;
    ImageButton btBack;
    ImageButton btCheck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_pfhone_number_in_settings_fragment);

        phoneET = (EditText) findViewById(R.id.settingsFrSetupPhoneInput);
        btBack = (ImageButton) findViewById(R.id.settingsFrSetupPhoneButtonBack);
        btCheck = (ImageButton) findViewById(R.id.settingsFrSetupPhoneButtonCheck);

        phoneET.setText(authManager.getPhoneNumber());
        btBack.setOnClickListener(this);
        btCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingsFrSetupPhoneButtonBack:
                onBackPressed();
                break;
            case R.id.settingsFrSetupPhoneButtonCheck:
                authManager.changePhoneNumber(phoneET.getText().toString());
                Intent intent = new Intent(SetupPhoneActivity.this, MenuScreenActivity.class);
                startActivity(intent);
                break;
        }
    }
}
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

public class SetupBioActivity extends AppCompatActivity implements View.OnClickListener {
    AuthenticationManager authManager = AuthenticationManager.getInstance();
    EditText bioET;
    ImageButton btBack;
    ImageButton btCheck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_bio_in_settings_fragment);

        bioET =  findViewById(R.id.settingsFrSetupBioInput);
        btBack = findViewById(R.id.settingsFrSetupBioButtonBack);
        btCheck = findViewById(R.id.settingsFrSetupBioButtonCheck);

        bioET.setText(authManager.getBio());
        btBack.setOnClickListener(this);
        btCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingsFrSetupBioButtonBack:
                onBackPressed();
                break;
            case R.id.settingsFrSetupBioButtonCheck:
                authManager.changeBio(bioET.getText().toString());
                Intent intent = new Intent(SetupBioActivity.this, MenuScreenActivity.class);
                startActivity(intent);
                break;
        }
    }
}

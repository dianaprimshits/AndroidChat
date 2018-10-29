package com.bigsur.AndroidChatWithMaps.settings;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.Home.MenuScreenActivity;
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

        bioET = (EditText) findViewById(R.id.settingsFrSetupBioInput);
        btBack = (ImageButton) findViewById(R.id.settingsFrSetupBioButtonBack);
        btCheck = (ImageButton) findViewById(R.id.settingsFrSetupBioButtonCheck);

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

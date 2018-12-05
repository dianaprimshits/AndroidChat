package com.bigsur.AndroidChatWithMaps.UI.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.UI.Home.MenuScreenActivity;
import com.bigsur.AndroidChatWithMaps.R;

public class SetupLoginActivity extends AppCompatActivity implements View.OnClickListener {
    AuthenticationManager authManager = AuthenticationManager.getInstance();
    EditText loginET;
    ImageButton btBack;
    ImageButton btCheck;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_login_in_settings_fragment);

        loginET = (EditText) findViewById(R.id.settingsFrSetupLoginInput);
        btBack = (ImageButton) findViewById(R.id.settingsFrSetupLoginButtonBack);
        btCheck = (ImageButton) findViewById(R.id.settingsFrSetupLoginButtonCheck);

        loginET.setText(authManager.getSavedCredentials().getLogin());
        btBack.setOnClickListener(this);
        btCheck.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingsFrSetupLoginButtonBack:
                onBackPressed();
                break;
            case R.id.settingsFrSetupLoginButtonCheck:
                String newLogin = loginET.getText().toString();
                if (authManager.loginValidationCheck(newLogin)) {
                    authManager.changeLogin(newLogin);
                    Toast.makeText(getApplicationContext(), "Login was changed successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SetupLoginActivity.this, MenuScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "This field can't be empty. Please input login.", Toast.LENGTH_SHORT);
                    TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
                    if( tv != null) {
                        tv.setGravity(Gravity.CENTER);
                    }
                    toast.show();
                }
                break;
        }
    }
}

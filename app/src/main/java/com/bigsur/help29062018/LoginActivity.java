package com.bigsur.help29062018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.security.KeyStore;
import java.util.List;
import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;

public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button signInButton;
    private Button loginButton;
    String TEXT_LOGIN;
    String TEXT_PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (EditText) findViewById(R.id.editLogin);
        password = (EditText) findViewById(R.id.editPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);
        signInButton = (Button) findViewById(R.id.buttonSignIn);

        try {
            String storeFileName = "securedStore";
            String keyPrefix = "vss";
            byte[] seedKey = "SecuredData".getBytes();
            SecuredPreferenceStore.init(getApplicationContext(), storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());

            setupStore();
        } catch (Exception e) {
            e.printStackTrace();
        }

        signInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean isComplete = false;
                try {
                    reloadData();
                    isComplete = true;
                } catch (Exception e) {
                    Log.e("SECURED-PREFERENCE", "", e);
                    Toast.makeText(LoginActivity.this, "An exception occurred, see log for details", Toast.LENGTH_SHORT).show();
                }
                if (isComplete & !(login.getText().toString().equals("")) & !(password.getText().toString().equals(""))) {
                    Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
                    startActivity(intent);
                    Log.d("!!!LOG!!!", String.format("login %s, password %s", login.getText(), password.getText()));
                } else {
                    signInButton.setEnabled(false);
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
                boolean isComplete = false;

                try {
                    saveData();
                    isComplete = true;
                } catch (Exception e) {
                    Log.e("SECURED-PREFERENCE", "", e);
                    Toast.makeText(LoginActivity.this, "An exception occurred, see log for details", Toast.LENGTH_SHORT).show();
                }
                if (isComplete & !(login.getText().toString().equals("")) & !(password.getText().toString().equals(""))) {
                    Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
                    startActivity(intent);
                    Log.d("!!!LOG!!!", String.format("login %s, password %s", prefStore.getString(TEXT_LOGIN, null), prefStore.getString(TEXT_PASSWORD, null)));
                }
            }
        });
    }

    private void setupStore() {
        SecuredPreferenceStore.setRecoveryHandler(new DefaultRecoveryHandler() {
            @Override
            protected boolean recover(Exception e, KeyStore keyStore, List<String> keyAliases, SharedPreferences preferences) {
                Toast.makeText(LoginActivity.this, "Encryption key got invalidated, will try to start over.", Toast.LENGTH_SHORT).show();
                return super.recover(e, keyStore, keyAliases, preferences);
            }
        });

        try {
            reloadData();
        } catch (Exception e) {
            Log.e("SECURED-PREFERENCE", "", e);
            Toast.makeText(this, "An exception occurred, see log for details", Toast.LENGTH_SHORT).show();
        }
    }

    void reloadData() {
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();

        String loginString = prefStore.getString(TEXT_LOGIN, null);
        String passString = prefStore.getString(TEXT_PASSWORD, null);

        if ((loginString != null) & (passString != null)) {
            login.setText(loginString);
            password.setText(passString);
        }
    }

    void saveData() {
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        String loginString = login.getText().toString();
        String passString = password.getText().toString();
        prefStore.edit().putString(TEXT_LOGIN, loginString.length() > 0 ? loginString : null).apply();
        prefStore.edit().putString(TEXT_PASSWORD, passString.length() > 0 ? passString : null).apply();
    }
}

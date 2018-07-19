package com.bigsur.help29062018;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.List;

import devliving.online.securedpreferencestore.DefaultRecoveryHandler;
import devliving.online.securedpreferencestore.SecuredPreferenceStore;

public class LoginActivity extends AppCompatActivity {

    private static String TAG = "!!LOG!!";
    private EditText login;
    private EditText password;
    private TextView loginLockedTextView;
    private TextView attemptsTextView;
    private TextView numberOfAttemptsTextView;
    private Button signInButton;
    private Button loginButton;

    private String dataToSave;



    int remainingLoginAttemptsNumber = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        login = (EditText)findViewById(R.id.editLogin);
        password = (EditText)findViewById(R.id.editPassword);

        dataToSave = String.format("%s-%s", login.getText().toString(), password.getText().toString());

        loginButton = (Button) findViewById(R.id.buttonLogin);
        signInButton = (Button)findViewById(R.id.buttonSignIn);




        loginLockedTextView = (TextView)findViewById(R.id.loginLocked);
        attemptsTextView = (TextView)findViewById(R.id.attempts);
        numberOfAttemptsTextView = (TextView)findViewById(R.id.attemptsNumber);
        numberOfAttemptsTextView.setText(Integer.toString(remainingLoginAttemptsNumber));

        try {
            String storeFileName = "securedStore";
            String keyPrefix = "vss";
            //it's better to provide one, and you need to provide the same key each time after the first time
            byte[] seedKey = "SecuredSeedData".getBytes();
            SecuredPreferenceStore.init(getApplicationContext(), storeFileName, keyPrefix, seedKey, new DefaultRecoveryHandler());

            setupStore();
        } catch (Exception e) {
            // Handle error.
            e.printStackTrace();
        }


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    reloadData();
                    Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("SECURED-PREFERENCE", "", e);
                    Toast.makeText(LoginActivity.this, "An exception occurred, see log for details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveData();
                    Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("SECURED-PREFERENCE", "", e);
                    Toast.makeText(LoginActivity.this, "An exception occurred, see log for details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void setupStore() {
        SecuredPreferenceStore.setRecoveryHandler(new DefaultRecoveryHandler(){
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




 /*   @Override
    public void onClick(View v) {
        saveData(dataToSave);
        Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
        startActivity(intent);


        //if (login.getText().toString().equals("bigsur") && password.getText().toString().equals("ananas11")) {
            Toast.makeText(getApplicationContext(), "login and password are correct",Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect login or password", Toast.LENGTH_SHORT)
                    .show();
            remainingLoginAttemptsNumber--;

            attemptsTextView.setVisibility(View.VISIBLE);
            numberOfAttemptsTextView.setVisibility(View.VISIBLE);
            numberOfAttemptsTextView.setText("   " + Integer.toString(remainingLoginAttemptsNumber));

            if (remainingLoginAttemptsNumber == 0) {
                signInButton.setEnabled(false);
                login.setEnabled(false);
                password.setEnabled(false);
                loginLockedTextView.setVisibility(View.VISIBLE);
                loginLockedTextView.setBackgroundColor(Color.RED);
                loginLockedTextView.setText("locked");
            }
        }//
    }*/

    void reloadData()  {
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();

        String[] loginPassword = prefStore.getString(dataToSave, null).split("-");
        login.setText(loginPassword[0]);
        password.setText(loginPassword[1]);
    }

    void saveData() {
        SecuredPreferenceStore prefStore = SecuredPreferenceStore.getSharedInstance();
        prefStore.edit().putString(dataToSave, dataToSave.length() > 0 ? dataToSave : null).apply();
    }

}

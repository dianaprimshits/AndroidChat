package com.bigsur.AndroidChatWithMaps.UI.startScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.Credentials;
import com.bigsur.AndroidChatWithMaps.UI.Home.MenuScreenActivity;
import com.bigsur.AndroidChatWithMaps.R;


public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button loginButton;
    AuthManager authManager = AuthenticationManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.editLogin);
        password = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.buttonLogin);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Credentials credentials = new Credentials(login.getText().toString(), password.getText().toString());
                authManager.login(credentials,
                        new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Incorrect login or password", Toast.LENGTH_LONG).show();
                            }
                        }
                );

            }
        });
    }

}

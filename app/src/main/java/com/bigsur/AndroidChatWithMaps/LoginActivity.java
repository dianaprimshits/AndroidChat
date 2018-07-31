package com.bigsur.AndroidChatWithMaps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.Credentials;
import com.bigsur.AndroidChatWithMaps.Home.MenuScreenActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText login;
    private EditText password;
    private Button signInButton;
    private Button loginButton;
    String TEXT_LOGIN = "loginString";
    String TEXT_PASSWORD = "passString";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (EditText) findViewById(R.id.editLogin);
        password = (EditText) findViewById(R.id.editPassword);
        loginButton = (Button) findViewById(R.id.buttonLogin);

        final AuthManager authManager = new AuthenticationManager();
        final Credentials credentials = new Credentials();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isComplete = false;

                try {
                    credentials.setLogin(login.getText().toString());
                    credentials.setPassword(password.getText().toString());
                    authManager.saveData(credentials);
                    isComplete = true;
                } catch (Exception e) {
                    Log.e("SECURED-PREFERENCE", "", e);
                    Toast.makeText(LoginActivity.this, "An exception occurred, see log for details", Toast.LENGTH_SHORT).show();
                }
                if (isComplete & !(login.getText().toString().equals("")) & !(password.getText().toString().equals(""))) {
                    Intent intent = new Intent(LoginActivity.this, MenuScreenActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

package com.bigsur.help29062018;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText login;
    private EditText password;
    private Button signInButton;
    private TextView loginLockedTextView;
    private TextView attemptsTextView;
    private TextView numberOfAttemptsTextView;

    int remainingLoginAttemptsNumber = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText)findViewById(R.id.editLogin);
        password = (EditText)findViewById(R.id.editPassword);

        signInButton = (Button)findViewById(R.id.buttonLogin);
        signInButton.setOnClickListener(this);

        loginLockedTextView = (TextView)findViewById(R.id.loginLocked);
        attemptsTextView = (TextView)findViewById(R.id.attempts);
        numberOfAttemptsTextView = (TextView)findViewById(R.id.attemptsNumber);
        numberOfAttemptsTextView.setText(Integer.toString(remainingLoginAttemptsNumber));
    }

    @Override
    public void onClick(View v) {
        if (login.getText().toString().equals("bigsur") && password.getText().toString().equals("ananas11")) {
            Toast.makeText(getApplicationContext(), "login and password are correct",Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent(MainActivity.this, MenuScreenActivity.class);
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
        }
    }
}

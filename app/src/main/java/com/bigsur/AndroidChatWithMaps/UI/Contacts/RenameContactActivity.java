package com.bigsur.AndroidChatWithMaps.UI.Contacts;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;

public class RenameContactActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LOG";
    DataWithIconManager dbManager = ViewableContactManager.getInstance();
    private static int DEFAULT_VALUE = -1;
    String contactName;
    int contactId;
    EditText contactNewNameET;
    ImageButton okButton;
    ProgressBar wait;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_rename_activity);

        Intent intent = getIntent();
        contactId = intent.getIntExtra("id", DEFAULT_VALUE);
        contactName = intent.getStringExtra("name");

        contactNewNameET = findViewById(R.id.renameContactActFirstNameET);
        contactNewNameET.setText(dbManager.getById(contactId).getName());
        okButton = findViewById(R.id.renameContactActButtonOk);
        wait = findViewById(R.id.progressBarRenameContact);
        toolbar = findViewById(R.id.renameContactActivityToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Rename contact");
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.renameContactActButtonOk:
                wait.setVisibility(ProgressBar.VISIBLE);
                okButton.setVisibility(View.GONE);
                DataWithIcon contacts = dbManager.getById(contactId);
                contacts.setName(contactNewNameET.getText().toString());
                dbManager.update(contacts,
                        () -> {
                            Toast.makeText(getApplicationContext(), "Contact updated", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        },
                        () -> {
                            Toast.makeText(getApplicationContext(), "Contact can't be updated\nTry later", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                );

        }
    }
}

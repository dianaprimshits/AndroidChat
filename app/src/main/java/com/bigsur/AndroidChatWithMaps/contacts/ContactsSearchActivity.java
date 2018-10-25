package com.bigsur.AndroidChatWithMaps.contacts;


import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.bigsur.AndroidChatWithMaps.R;

public class ContactsSearchActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    ImageButton btBack;
    MenuItem searchItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_search_activity);

        toolbar = (Toolbar) findViewById(R.id.contacts_search_act_toolbar);
        btBack = (ImageButton) findViewById(R.id.contactsActBtBack);
        btBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contscts_search_act_menu, menu);
        this.searchItem = menu.findItem(R.id.contactSearchActivitySearchItem);
        ((SearchView) searchItem.getActionView()).onActionViewExpanded();
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search People");

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contactsActBtBack:
                onBackPressed();
                break;
        }
    }
}

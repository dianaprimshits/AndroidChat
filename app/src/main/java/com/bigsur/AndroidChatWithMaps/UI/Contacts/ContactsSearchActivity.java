package com.bigsur.AndroidChatWithMaps.UI.Contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bigsur.AndroidChatWithMaps.R;


public class ContactsSearchActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton btBack;
    MenuItem searchItem;
    SearchView searchView;
    ListView lvSearchResult;

    Fragment selectedFragment = null;
    ContactsSearchBeforeTextInput beforeTextInputFr;
    ContactsSearchAfterTextInput afterTextInputFr;
    FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_search_activity);

        beforeTextInputFr = ContactsSearchBeforeTextInput.newInstance();
        afterTextInputFr = ContactsSearchAfterTextInput.newInstance();

        lvSearchResult = (ListView) findViewById(R.id.lvMain);
        toolbar = (Toolbar) findViewById(R.id.contacts_search_act_toolbar);
        btBack = (ImageButton) findViewById(R.id.contactsActBtBack);
        btBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        selectedFragment = beforeTextInputFr;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.contactSearchContactsNumberLT, selectedFragment);
        transaction.commit();

        getMenuInflater().inflate(R.menu.contacts_search_act_menu, menu);
        searchItem = menu.findItem(R.id.contactSearchActivitySearchItem);
        ((SearchView) searchItem.getActionView()).onActionViewExpanded();
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search People");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            Fragment before = new ContactsSearchBeforeTextInput();
            ContactsSearchAfterTextInput after = new ContactsSearchAfterTextInput();
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    selectedFragment = before;
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contactSearchContactsNumberLT, selectedFragment);
                    transaction.commit();
                    return true;
                } else {
                    selectedFragment = after;
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contactSearchContactsNumberLT, selectedFragment);
                    transaction.commit();
                    after.setSearchTextChange(newText);
                    return true;
                }
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                selectedFragment = new ContactsSearchAfterTextInput();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contactSearchContactsNumberLT, selectedFragment);
                transaction.commit();
                return true;
            }
        });

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

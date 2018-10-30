package com.bigsur.AndroidChatWithMaps.contacts;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bigsur.AndroidChatWithMaps.DBManager.Adapters.AdapterForChatsSearchResult;
import com.bigsur.AndroidChatWithMaps.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class ContactsSearchActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageButton btBack;
    MenuItem searchItem;
    SearchView searchView;
    AdapterForChatsSearchResult searchAdapter;
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

        lvSearchResult = findViewById(R.id.lvMain);
        toolbar = findViewById(R.id.contacts_search_act_toolbar);
        btBack = findViewById(R.id.contactsActBtBack);
        btBack.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        selectedFragment = beforeTextInputFr;

        getMenuInflater().inflate(R.menu.contscts_search_act_menu, menu);
        this.searchItem = menu.findItem(R.id.contactSearchActivitySearchItem);
        ((SearchView) searchItem.getActionView()).onActionViewExpanded();
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search People");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty() || !(newText == null)) {
                    selectedFragment = new ContactsSearchAfterTextInput();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contactSearchContactsNumberLT, selectedFragment);
                    transaction.commit();
                }
                return true;
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

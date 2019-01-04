package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;

import java.util.ArrayList;

public class AddGroupActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DataWithIconManager dbStorage = ViewableContactManager.getInstance();
    AdapterForGroupAdd adapterForChatAdd;
    DataWithIconListview lvMain;
    ImageButton backBt;
    SearchView searchView;
    ConstraintLayout checkBoxLL;
    ArrayList<DataWithIcon> contacts = new ArrayList<>();
    ImageView forwardBT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_activity);


        toolbar = findViewById(R.id.group_add_act_toolbar);
        lvMain = findViewById(R.id.lvMain);
        backBt = findViewById(R.id.groupAddActBtBack);
        checkBoxLL = findViewById(R.id.checkableBtLL);
        searchView = findViewById(R.id.groupAddActSearchItem);
        forwardBT = findViewById(R.id.forwardBT);

        backBt.setOnClickListener(this);
        forwardBT.setOnClickListener(this);


        adapterForChatAdd = new AdapterForGroupAdd(getApplicationContext(), dbStorage.getAll());
        lvMain.init(dbStorage, adapterForChatAdd, "contacts", this);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapterForChatAdd.setCheckBoxVisibility(view);

                if (adapterForChatAdd.getCheckBoxVisibility(view) == View.VISIBLE) {
                    if (!contacts.contains(adapterForChatAdd.getItem(position))) {
                        contacts.add(adapterForChatAdd.getItem(position));
                    }
                } else if (adapterForChatAdd.getCheckBoxVisibility(view) == View.GONE) {
                    if (contacts.contains(adapterForChatAdd.getItem(position))) {
                        contacts.remove(adapterForChatAdd.getItem(position));
                    }
                }
                if (contacts.size() == 0) {
                    forwardBT.setVisibility(View.INVISIBLE);
                } else {
                    forwardBT.setVisibility(View.VISIBLE);
                }
                Toast.makeText(getApplicationContext(), contacts.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        lvMain.setAdapter(adapterForChatAdd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        searchView.setQueryHint("Search");
        searchView.setIconifiedByDefault(false);
        ImageView searchViewIcon = searchView.findViewById(R.id.search_mag_icon);
        ViewGroup linearLayoutSearchView = (ViewGroup) searchViewIcon.getParent();
        linearLayoutSearchView.removeView(searchViewIcon);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterForChatAdd.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.contact_fr_search:

                return true;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.groupAddActBtBack:
                onBackPressed();
                break;
            case R.id.forwardBT:
                Intent dialogIntent = new Intent(this, AddGroupNameActivity.class);

                for(int i = 0; i < contacts.size(); i++) {
                    dialogIntent.putExtra("contactId" + i, contacts.get(i).getId());
                }

                dialogIntent.putExtra("numberOfContacts", contacts.size());
                startActivity(dialogIntent);
        }

    }
}


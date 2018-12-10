package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;

public class AddChatActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DataWithIconManager dbStorage = ViewableContactManager.getInstance();
    AdapterForChatAdd adapterForChatAdd;
    DataWithIconListview lvMain;
    ImageButton backBt;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog_activity);


        toolbar = (Toolbar) findViewById(R.id.chat_add_act_toolbar);
        lvMain = (DataWithIconListview) findViewById(R.id.lvMain);
        backBt = (ImageButton) findViewById(R.id.chatAddActBtBack);
        searchView = (SearchView) findViewById(R.id.chatAddActSearchItem);

        backBt.setOnClickListener(this);
        adapterForChatAdd = new AdapterForChatAdd(getApplicationContext(), dbStorage.getAll());
        lvMain.init(dbStorage, adapterForChatAdd, "contacts", this);
        lvMain.setAdapter(adapterForChatAdd);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_chat_add_menu, menu);
        searchView.setQueryHint("Search");
        searchView.setIconifiedByDefault(false);
        ImageView searchViewIcon = (ImageView) searchView.findViewById(R.id.search_mag_icon);
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
            case R.id.chatAdd:
                onClickAddContactButton();
                return true;
            case R.id.contact_fr_search:

                return true;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.chatAddActBtBack:
                onBackPressed();
                break;
        }

    }

    public void onClickAddContactButton() {

    }
}

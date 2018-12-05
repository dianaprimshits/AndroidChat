package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.Contacts;
import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableChat.ViewableChatManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.concurrent.ExecutionException;

public class AddChatActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    DataWithIconManager dbStorage = ViewableChatManager.getInstance();
    AdapterForChatAdd adapterForChatAdd;
    ListView lvMain;
    ImageButton backBt;
    android.support.v7.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog_activity);


        toolbar = (Toolbar) findViewById(R.id.chat_add_act_toolbar);
        lvMain = (ListView) findViewById(R.id.lvMain);
        backBt = (ImageButton) findViewById(R.id.chatAddActBtBack);
        searchView = (android.support.v7.widget.SearchView) findViewById(R.id.chatAddActSearchItem);

        backBt.setOnClickListener(this);


        adapterForChatAdd = new AdapterForChatAdd(getApplicationContext(), dbStorage.getAll());

        lvMain.setAdapter(adapterForChatAdd);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dialogIntent = new Intent(AddChatActivity.this, DialogActivity.class);
                dialogIntent.putExtra("name", adapterForChatAdd.getItem(position).getName());
                Log.d("!!!!!!!!LOG!!!!!!!", "onItemClick:" + adapterForChatAdd.getItem(position).getClass().getSimpleName());
                Log.d("!!!!!!!!LOG!!!!!!!", "onItemClick:" + adapterForChatAdd.getItem(position).getId());
                dialogIntent.putExtra("id", adapterForChatAdd.getItem(position).getId());
                dialogIntent.putExtra("coming from", "contacts");
                startActivity(dialogIntent);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.top_chat_add_menu, menu);
        searchView.setQueryHint("Search");
        searchView.setIconifiedByDefault(false);
        ImageView searchViewIcon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
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
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View dialog = li.inflate(R.layout.create_contact, null);
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        mDialogBuilder.setView(dialog);
        final EditText alterDialogName = (EditText) dialog.findViewById(R.id.contactName);
        final EditText alterDialogPhoneNumber = (EditText) dialog.findViewById(R.id.contactPhoneNumber);
        mDialogBuilder
                .setCancelable(false)
                .setPositiveButton("create contact",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DataWithIcon contact = new Contacts(alterDialogName.getText().toString(), alterDialogPhoneNumber.getText().toString());
                                dbStorage.create(contact);
                                try {
                                    refreshListView();
                                } catch (ExecutionException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();

        final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams buttonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        buttonLL.weight = 1;
        buttonLL.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(buttonLL);
        negativeButton.setLayoutParams(buttonLL);
    }

    private void refreshListView() throws ExecutionException, InterruptedException {
        adapterForChatAdd = new AdapterForChatAdd(getApplicationContext(), dbStorage.getAll());
        lvMain.setAdapter(adapterForChatAdd);
    }


}

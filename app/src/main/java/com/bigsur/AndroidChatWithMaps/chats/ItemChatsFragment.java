package com.bigsur.AndroidChatWithMaps.chats;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.DBManager.Adapters.AdapterForChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Adapters.AdapterForChatsSearchResult;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.concurrent.ExecutionException;


public class ItemChatsFragment extends Fragment {
    private static final String TAG = "!!!LOG!!!";
    ListView lvMain;
    Toolbar toolbar;
    AdapterForChatRooms adapter;
    AdapterForChatsSearchResult searchAdapter;
    SQLiteContactsManager dbStorage = new SQLiteContactsManager();


    public static ItemChatsFragment newInstance() {
        ItemChatsFragment fragment = new ItemChatsFragment();
        Log.d(TAG, "newInstance: ItemChatsFragment ");
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        findViewsById(view);


        SQLiteContactsManager contactsManager = new SQLiteContactsManager();
        SQLiteChatRoomsManager chatRoomsManager = new SQLiteChatRoomsManager();

        try {
            adapter = new AdapterForChatRooms(getContext(), chatRoomsManager.getAll());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        lvMain.setAdapter(adapter);


        lvMain.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View dialog = li.inflate(R.layout.long_click_chat_rooms_listview, null);
                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                mDialogBuilder.setView(dialog);
                final TextView alterDialogName = (TextView) dialog.findViewById(R.id.chatRoomName);

                alterDialogName.setText(adapter.getItem(position).getName());
                mDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setNegativeButton("delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dbStorage.delete(adapter.getItem(position).getId());
                                        try {
                                            refreshDialogList();
                                        } catch (ExecutionException | InterruptedException e) {
                                            e.printStackTrace();
                                        }
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

                Toast.makeText(getContext(), "Item clicked", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.top_chat_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            SQLiteContactsManager contactsManager = new SQLiteContactsManager();
            SQLiteChatRoomsManager chatRoomsManager = new SQLiteChatRoomsManager();


            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if(searchAdapter == null) {
                        searchAdapter = new AdapterForChatsSearchResult(getContext(), contactsManager.getAll(), chatRoomsManager.getAll());
                        lvMain.setAdapter(searchAdapter);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                searchAdapter.getFilter().filter(newText);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    searchAdapter = new AdapterForChatsSearchResult(getContext(), contactsManager.getAll(), chatRoomsManager.getAll());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                searchAdapter.getFilter().filter(query);
                lvMain.setAdapter(searchAdapter);
                return true;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent dialogIntent = new Intent(getActivity(), DialogActivity.class );
                  startActivity(dialogIntent);
              }
          }
        );

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addDialog:
                onClickAddDialogButton();
                return true;

            case R.id.addGroupDialog:
                // do smth
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void findViewsById(View view) {
        lvMain = (ListView) view.findViewById(R.id.lvMain);
        toolbar = (Toolbar) view.findViewById(R.id.chat_toolbar);
    }


    private void refreshDialogList() throws ExecutionException, InterruptedException {
        SQLiteContactsManager contactsManager = new SQLiteContactsManager();
        SQLiteChatRoomsManager chatRoomsManager = new SQLiteChatRoomsManager();

        try {
            adapter = new AdapterForChatRooms(getContext(), chatRoomsManager.getAll());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        lvMain.setAdapter(adapter);
    }


    public void onClickAddDialogButton() {
        LayoutInflater li = LayoutInflater.from(getContext());
        View dialog = li.inflate(R.layout.create_contact, null);
        //открывается список как при search, только с одними контактами


    }
}




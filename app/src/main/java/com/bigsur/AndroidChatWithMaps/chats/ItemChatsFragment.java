package com.bigsur.AndroidChatWithMaps.chats;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class ItemChatsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "!!!LOG!!!";
    ListView lvMain;
    Toolbar toolbar;
    AdapterForChatRooms adapter;
    AdapterForChatsSearchResult searchAdapter;
    SQLiteContactsManager dbStorage = new SQLiteContactsManager();
    ImageButton imageButtonAdd;
    ImageButton imageButtonClose;
  //  ConstraintLayout chatsAddingTranslucentLt;
    Animation animationRotateCenter;
    ImageButton chatAddBt;
    ImageButton groupChatAddBt;
    TextView newChatTv;
    TextView newGroupTv;


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

        imageButtonAdd.setOnClickListener(this);
        imageButtonClose.setOnClickListener(this);


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
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Search");

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
                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent dialogIntent = new Intent(getActivity(), DialogActivity.class );
                        dialogIntent.putExtra("contactName", searchAdapter.getItem(position).getName());
                        startActivity(dialogIntent);
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                try {
                    searchAdapter = new AdapterForChatsSearchResult(getContext(), contactsManager.getAll(), chatRoomsManager.getAll());
                    lvMain.setAdapter(searchAdapter);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                searchAdapter.getFilter().filter(query);
                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent dialogIntent = new Intent(getActivity(), DialogActivity.class );
                        startActivity(dialogIntent);
                    }
                });
                return true;
            }
        });

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
        imageButtonAdd = (ImageButton) view.findViewById(R.id.chatsAddingBt);
       // chatsAddingTranslucentLt = (ConstraintLayout) view.findViewById(R.id.chatsAddingTranslucentLayout);
        imageButtonClose = (ImageButton) view.findViewById(R.id.chatsAddingTranslucentLayoutBtClose);
        animationRotateCenter = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_center);
        //chatAddBt = (ImageButton) view.findViewById(R.id.chatAddButton);
        groupChatAddBt = (ImageButton) view.findViewById(R.id.groupChatAddButton);
        newChatTv = (TextView) view.findViewById(R.id.addDialogTv);
        newGroupTv = (TextView) view.findViewById(R.id.addGroupDialogTv);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatsAddingBt:

                chatAddBt.setScaleX(40.0f);
                chatAddBt.setScaleY(40.0f);
                groupChatAddBt.setScaleX(40.0f);
                groupChatAddBt.setScaleY(40.0f);
                newChatTv.setScaleX(70.0f);
                newChatTv.setScaleY(30.0f);
                newGroupTv.setScaleX(80.0f);
                newGroupTv.setScaleY(30.0f);


                imageButtonAdd.setAnimation(animationRotateCenter);
              /*  up = AnimationUtils.loadAnimation(getContext(), R.anim.layout_up);
                chatAddBt.setAnimation(up);
                groupChatAddBt.setAnimation(up);
                newChatTv.setAnimation(up);
                newGroupTv.setAnimation(up);
                addDialogLt.setAnimation(up);
                addGroupLt.setAnimation(up);
                */



             //   chatsAddingTranslucentLt.setVisibility(VISIBLE);
                imageButtonClose.setVisibility(VISIBLE);
                imageButtonAdd.setVisibility(GONE);
                Log.d(TAG, "onClick: !!!!!!!!!!!!!!!!!!!!!!!!");
                break;

            case R.id.chatsAddingTranslucentLayoutBtClose:

               /* down = AnimationUtils.loadAnimation(getContext(), R.anim.layout_up);
                chatAddBt.setAnimation(down);
                groupChatAddBt.setAnimation(down);
                newChatTv.setAnimation(down);
                newGroupTv.setAnimation(down);
                addDialogLt.setAnimation(down);
                */


                imageButtonClose.setVisibility(GONE);
                imageButtonAdd.setVisibility(VISIBLE);
               // chatsAddingTranslucentLt.setVisibility(GONE);
                break;
           /* case R.id.chatAddButton:
                //do smth
                break;
            case R.id.addGroupDialog:
                //do smth
                break;*/
        }
    }

}




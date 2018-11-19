package com.bigsur.AndroidChatWithMaps.chats;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteChatRoomsManager;
import com.bigsur.AndroidChatWithMaps.DBManager.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.contacts.ContactsSearchActivity;

import java.util.concurrent.ExecutionException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class ItemChatsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "!!!LOG!!!";
    ListView lvMain;
    Toolbar toolbar;
    AdapterForChatRooms adapter;
    SQLiteContactsManager dbStorage = new SQLiteContactsManager();
    ImageButton imageButtonAdd;
    ImageButton imageButtonClose;
    ConstraintLayout chatsAddingTranslucentLt;
    ConstraintLayout addDialogBlock;
    ImageButton chatAddBt;
    ImageButton groupChatAddBt;
    TextView newChatTv;
    TextView newGroupTv;
    Animation up;
    Animation down;


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
        SQLiteChatRoomsManager chatRoomsManager = new SQLiteChatRoomsManager();

        try {
            adapter = new AdapterForChatRooms(getContext(), chatRoomsManager.getAll());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        lvMain.setAdapter(adapter);


        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent dialogIntent = new Intent(getActivity(), DialogActivity.class );
                        ChatRooms chatRoom = (ChatRooms) adapter.getItem(position);
                        dialogIntent.putExtra("contactName", chatRoom.getName());

                        dialogIntent.putExtra("id", chatRoom.getId());
                        dialogIntent.putExtra("coming from", "chatRooms");

                        startActivity(dialogIntent);
                    }
                });
            }
        });


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
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.chat_search_button:
                Intent searchIntent = new Intent(getActivity(), ContactsSearchActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void findViewsById(View view) {
        lvMain = (ListView) view.findViewById(R.id.lvMain);
        toolbar = (Toolbar) view.findViewById(R.id.chat_toolbar);
        imageButtonAdd = (ImageButton) view.findViewById(R.id.chatsAddingBt);
        chatsAddingTranslucentLt = (ConstraintLayout) view.findViewById(R.id.chatsAddingTranslucentLayout);
        imageButtonClose = (ImageButton) view.findViewById(R.id.chatsAddingTranslucentLayoutBtClose);
        chatAddBt = (ImageButton) view.findViewById(R.id.chatAddButton);
        groupChatAddBt = (ImageButton) view.findViewById(R.id.groupChatAddButton);
        newChatTv = (TextView) view.findViewById(R.id.addDialogTv);
        newGroupTv = (TextView) view.findViewById(R.id.addGroupDialogTv);
        addDialogBlock = (ConstraintLayout) view.findViewById(R.id.addDialogBlock);
    }


    private void refreshDialogList() throws ExecutionException, InterruptedException {
        SQLiteChatRoomsManager chatRoomsManager = new SQLiteChatRoomsManager();

        try {
            adapter = new AdapterForChatRooms(getContext(), chatRoomsManager.getAll());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        lvMain.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatsAddingBt:

                Animation animationRotateCenterRight = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_center_right);
                imageButtonAdd.setAnimation(animationRotateCenterRight);
                up = AnimationUtils.loadAnimation(getContext(), R.anim.layout_up);
                chatAddBt.setAnimation(up);
                groupChatAddBt.setAnimation(up);
                newChatTv.setAnimation(up);
                newGroupTv.setAnimation(up);
                addDialogBlock.setAnimation(up);

                chatsAddingTranslucentLt.setVisibility(VISIBLE);
                imageButtonClose.setVisibility(VISIBLE);
                imageButtonAdd.setVisibility(GONE);
                Log.d(TAG, "onClick: !!!!!!!!!!!!!!!!!!!!!!!!");
                break;

            case R.id.chatsAddingTranslucentLayoutBtClose:

                down = AnimationUtils.loadAnimation(getContext(), R.anim.layout_down);
                chatAddBt.setAnimation(down);
                groupChatAddBt.setAnimation(down);
                newChatTv.setAnimation(down);
                newGroupTv.setAnimation(down);
                addDialogBlock.setAnimation(down);

                Animation animationRotateCenterLeft = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_center_left);
                imageButtonClose.setAnimation(animationRotateCenterLeft);
                imageButtonClose.setVisibility(GONE);
                imageButtonAdd.setVisibility(VISIBLE);
                imageButtonAdd.setAnimation(animationRotateCenterLeft);
                chatsAddingTranslucentLt.setVisibility(GONE);
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




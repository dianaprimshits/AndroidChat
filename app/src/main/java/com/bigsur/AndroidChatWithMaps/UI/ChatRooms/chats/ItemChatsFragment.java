package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.ContactsSearchActivity;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.CustomContactsAdapter;
import com.bigsur.AndroidChatWithMaps.UI.DataModifierView.ChatModifier;
import com.bigsur.AndroidChatWithMaps.UI.DataModifierView.DataModifier;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class ItemChatsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "!!!LOG!!!";
    DataWithIconListview lvMain;
    Toolbar toolbar;
    ViewableContactManager dbStorage = ViewableContactManager.getInstance();
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
        setOnClickListener();

        lvMain.init(dbStorage, new CustomContactsAdapter(getContext(), dbStorage), "chatRooms", getActivity());


        lvMain.setOnItemLongClickListener((parent, view1, position, id) -> {
            DataModifier dataModifier = new ChatModifier(getContext());
            dataModifier.init(getContext(), new CustomContactsAdapter(getContext(), dbStorage), position, getActivity());
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
            AlertDialog alertDialog = mDialogBuilder.setView(dataModifier.getView()).create();
            alertDialog.show();
            return true;
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
        lvMain = view.findViewById(R.id.lvMain);
        toolbar = view.findViewById(R.id.chat_toolbar);
        imageButtonAdd = view.findViewById(R.id.chatsAddingBt);
        chatsAddingTranslucentLt = view.findViewById(R.id.chatsAddingTranslucentLayout);
        imageButtonClose = view.findViewById(R.id.chatsAddingTranslucentLayoutBtClose);
        chatAddBt = view.findViewById(R.id.chatAddButton);
        groupChatAddBt = view.findViewById(R.id.groupChatAddButton);
        newChatTv = view.findViewById(R.id.addDialogTv);
        newGroupTv = view.findViewById(R.id.addGroupDialogTv);
        addDialogBlock = view.findViewById(R.id.addDialogBlock);
    }

    private void setOnClickListener() {
        imageButtonAdd.setOnClickListener(this);
        imageButtonClose.setOnClickListener(this);
        chatAddBt.setOnClickListener(this);
        groupChatAddBt.setOnClickListener(this);
        chatsAddingTranslucentLt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chatsAddingBt:
                setMainElementsEnableFalse();
                openAddChatBlock();
                break;

            case R.id.chatsAddingTranslucentLayoutBtClose:
                setMainElementsEnableTrue();
                closeChatAddBlock();
                break;

            case R.id.chatAddButton:
                Intent addChatIntent = new Intent(getActivity(), AddChatActivity.class);
                startActivity(addChatIntent);
                break;

            case R.id.groupChatAddButton:
                Intent addGroupIntent = new Intent(getActivity(), AddGroupActivity.class);
                startActivity(addGroupIntent);
                break;

            case R.id.chatsAddingTranslucentLayout:
                setMainElementsEnableTrue();
                closeChatAddBlock();

        }
    }

    private void setMainElementsEnableTrue() {
        lvMain.setEnabled(true);
        toolbar.setEnabled(true);
        imageButtonAdd.setEnabled(true);
    }

    private void setMainElementsEnableFalse() {
        lvMain.setEnabled(false);
        toolbar.setEnabled(false);
        imageButtonAdd.setEnabled(false);
    }

    private void openAddChatBlock() {
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
        chatAddBt.setVisibility(VISIBLE);
    }

    private void closeChatAddBlock() {
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
    }
}




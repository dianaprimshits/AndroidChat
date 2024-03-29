package com.bigsur.AndroidChatWithMaps.UI.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats.ItemChatsFragment;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.ItemContactsFragment;
import com.bigsur.AndroidChatWithMaps.UI.maps.ItemMapsFragment;
import com.bigsur.AndroidChatWithMaps.UI.settings.FiaskoFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;




public class MenuScreenActivity extends AppCompatActivity {

    private static final String TAG = "!!!LOG!!!";
    Fragment mapsFragment = ItemMapsFragment.newInstance();
    Fragment contactsFragment = ItemContactsFragment.newInstance();
    Fragment chatsFragment = ItemChatsFragment.newInstance();
    Fragment settingsFragment = FiaskoFragment.newInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen_activity);
        setupBottomNavigationView();
    }
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        bottomNavigationView.enableAnimation(false);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setTextVisibility(false);



        bottomNavigationView.setOnNavigationItemSelectedListener
                (item -> {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.icon_maps:
                            selectedFragment = mapsFragment;
                            break;
                        case R.id.icon_contacts:
                            selectedFragment = contactsFragment;
                            break;
                        case R.id.icon_chats:
                            selectedFragment = chatsFragment;
                            break;
                        case R.id.icon_settings:
                            selectedFragment = settingsFragment;
                            break;
                    }
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                });
    }
}
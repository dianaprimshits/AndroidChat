package com.bigsur.help29062018.Home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.bigsur.help29062018.R;
import com.bigsur.help29062018.Utils.BottomNavigationViewHelper;
import com.bigsur.help29062018.chats.ItemChatsFragment;
import com.bigsur.help29062018.maps.ItemMapsFragment;
import com.bigsur.help29062018.settings.ItemSettingsFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class MenuScreenActivity extends AppCompatActivity {

    private static final String TAG = "!!!LOG!!!";
    private static final int ACTIVITY_NUM = 0;

    Fragment mapsFragment = ItemMapsFragment.newInstance();
    Fragment chatsFragment = ItemChatsFragment.newInstance();
    Fragment settingsFragment = ItemSettingsFragment.newInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_screen_activity);

        setupBottomNavigationView();

    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        bottomNavigationViewEx.setOnNavigationItemSelectedListener
                (new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.icon_maps:
                                selectedFragment = mapsFragment;
                                break;
                            case R.id.icon_chats:
                                selectedFragment = chatsFragment;
                                break;
                            case R.id.icon_settings:
                                selectedFragment = settingsFragment;
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
    }
}


package com.bigsur.AndroidChatWithMaps.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthManager;
import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.startScreen.LoginActivity;

public class ItemSettingsFragment extends Fragment implements View.OnClickListener{

    AuthManager authManager = AuthenticationManager.getInstance();
    Toolbar toolbar;
    TextView textView;
    ImageButton editUserNameButton;


    public static ItemSettingsFragment newInstance() {
        ItemSettingsFragment fragment = new ItemSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        findViewsById(view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);
        textView.setText(authManager.getSavedCredentials().getLogin());

        editUserNameButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         super.onCreateOptionsMenu(menu, inflater);
         inflater.inflate(R.menu.settings_toolbar_menu, menu);
    }


    private void findViewsById(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.settings_toolbar);
        textView = (TextView) view.findViewById(R.id.settingsTVUsername);
        editUserNameButton = (ImageButton) view.findViewById(R.id.settingsFrEditNameButton);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.settingsFrLogout:

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);



            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settingsFrEditNameButton:
                Intent intent = new Intent(getActivity(), UserInfoEditActivity.class);
                startActivity(intent);
                break;
        }
    }
}


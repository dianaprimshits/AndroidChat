package com.bigsur.AndroidChatWithMaps.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.startScreen.LoginActivity;

public class ItemSettingsFragment extends Fragment implements View.OnClickListener{

    AuthenticationManager authManager = AuthenticationManager.getInstance();
    Toolbar toolbar;
    TextView tvUsername;
    TextView tvLogin;
    TextView tvPhone;
    TextView tvBio;
    ImageButton editUserNameButton;
    ConstraintLayout userInfoLL;
    ConstraintLayout phoneLL;
    ConstraintLayout bioLL;


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

        tvUsername.setText(authManager.getUsername());
        tvLogin.setText(authManager.getSavedCredentials().getLogin());
        tvPhone.setText(authManager.getPhoneNumber());
        tvBio.setText(authManager.getBio());

        editUserNameButton.setOnClickListener(this);
        userInfoLL.setOnClickListener(this);
        phoneLL.setOnClickListener(this);
        bioLL.setOnClickListener(this);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         super.onCreateOptionsMenu(menu, inflater);
         inflater.inflate(R.menu.settings_toolbar_menu, menu);
    }


    private void findViewsById(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.dialog_toolbar);
        tvUsername = (TextView) view.findViewById(R.id.settingsTVUsername);
        tvLogin = (TextView) view.findViewById(R.id.settingsFrSetupUserNameTV);
        tvBio = (TextView) view.findViewById(R.id.settingsFrTVBio);
        tvPhone = (TextView) view.findViewById(R.id.settingsFrTVPhone);
        editUserNameButton = (ImageButton) view.findViewById(R.id.settingsFrEditNameButton);
        userInfoLL = (ConstraintLayout) view.findViewById(R.id.settingsFrUsername);
        phoneLL = (ConstraintLayout) view.findViewById(R.id.settingsFrPhoneNumber);
        bioLL = (ConstraintLayout) view.findViewById(R.id.settingsFrBio);
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
                Intent intent = new Intent(getActivity(), SetupUsernameActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsFrUsername:
                Intent intentSetupUserName = new Intent(getActivity(), SetupLoginActivity.class);
                startActivity(intentSetupUserName);
                break;
            case R.id.settingsFrPhoneNumber:
                Intent intentSetupPhone = new Intent(getActivity(), SetupPhoneActivity.class);
                startActivity(intentSetupPhone);
                break;
            case R.id.settingsFrBio:
                Intent intentSetupBio = new Intent(getActivity(), SetupBioActivity.class);
                startActivity(intentSetupBio);
                break;
        }
    }
}


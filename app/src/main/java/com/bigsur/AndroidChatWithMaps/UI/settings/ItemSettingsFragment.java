package com.bigsur.AndroidChatWithMaps.UI.settings;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataModifierView.DataModifier;
import com.bigsur.AndroidChatWithMaps.UI.DataModifierView.UserAvatarModifier;
import com.bigsur.AndroidChatWithMaps.UI.startScreen.LoginActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.VISIBLE;

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
    DataModifier dataModifier;
    CircleImageView userAvatar;
    ConstraintLayout modifierAvatarLL;
    ConstraintLayout photoLL;
    ConstraintLayout settingsLL;
    Animation up;
    Animation down;
    ConstraintLayout openPhotoLL;
    ConstraintLayout openCameraLL;
    ConstraintLayout openGalleryLL;
    ConstraintLayout deletePhotoLL;


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

        userAvatar.setOnClickListener(this);
        editUserNameButton.setOnClickListener(this);
        userInfoLL.setOnClickListener(this);
        phoneLL.setOnClickListener(this);
        bioLL.setOnClickListener(this);
        openCameraLL.setOnClickListener(this);
        openGalleryLL.setOnClickListener(this);
        openPhotoLL.setOnClickListener(this);
        deletePhotoLL.setOnClickListener(this);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         super.onCreateOptionsMenu(menu, inflater);
         inflater.inflate(R.menu.settings_toolbar_menu, menu);
    }


    private void findViewsById(View view) {
        toolbar =  view.findViewById(R.id.dialog_toolbar);
        tvUsername =  view.findViewById(R.id.settingsTVUsername);
        tvLogin =  view.findViewById(R.id.settingsFrSetupUserNameTV);
        tvBio =  view.findViewById(R.id.settingsFrTVBio);
        tvPhone =  view.findViewById(R.id.settingsFrTVPhone);
        editUserNameButton =  view.findViewById(R.id.settingsFrEditNameButton);
        userInfoLL =  view.findViewById(R.id.settingsFrUsername);
        phoneLL =  view.findViewById(R.id.settingsFrPhoneNumber);
        bioLL = view.findViewById(R.id.settingsFrBio);
        userAvatar = view.findViewById(R.id.usesrAvatar);
        modifierAvatarLL = view.findViewById(R.id.modifierAvatarLL);
        photoLL = view.findViewById(R.id.photoLayout);
        settingsLL =  view.findViewById(R.id.settingsFrUserInfo);
        openPhotoLL = view.findViewById(R.id.openPhotoLL);
        openCameraLL = view.findViewById(R.id.openCameraLL);
        openGalleryLL = view.findViewById(R.id.openGalleryLL);
        deletePhotoLL = view.findViewById(R.id.deleteAvatarLL);
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
            case R.id.usesrAvatar:
                dataModifier = new UserAvatarModifier(getContext());
                dataModifier.init1(getContext());

                up = AnimationUtils.loadAnimation(getContext(), R.anim.layout_up);
                modifierAvatarLL.setAnimation(up);
                modifierAvatarLL.setVisibility(VISIBLE);
                break;
            case R.id.openPhotoLL:
                Toast.makeText(getContext(), "open photo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.openCameraLL:
                Toast.makeText(getContext(), "open camera", Toast.LENGTH_SHORT).show();
                break;
            case R.id.openGalleryLL:
                Toast.makeText(getContext(), "open gallery", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteAvatarLL:
                Toast.makeText(getContext(), "delete photo", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


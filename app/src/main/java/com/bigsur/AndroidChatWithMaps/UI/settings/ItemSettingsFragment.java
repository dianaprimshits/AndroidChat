package com.bigsur.AndroidChatWithMaps.UI.settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.startScreen.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;

public class ItemSettingsFragment extends Fragment implements OnClickListener {

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
    ConstraintLayout modifierAvatarLL;
    ConstraintLayout photoLL;
    ConstraintLayout settingsLL;
    Animation up;
    Animation down;
    ConstraintLayout translucentLL;
    ConstraintLayout modifierLL;
    ConstraintLayout openGalleryLL;
    ConstraintLayout deletePhotoLL;
    ConstraintLayout mainEntrails;
    private int PICK_IMAGE_REQUEST = 1;
    CircleImageView userAvatar;
    byte[] avatarBytes;

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

        byte[] array = Base64.decode(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("AVATAR", "defaultStringIfNothingFound"), Base64.DEFAULT);
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getString("AVATAR", "defaultStringIfNothingFound").equals("defaultStringIfNothingFound")){
            userAvatar.setImageResource(R.drawable.default_avatar);
        } else {
            userAvatar.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        }

        setOnClickListener();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_toolbar_menu, menu);
    }


    private void setOnClickListener() {
        userAvatar.setOnClickListener(this);
        editUserNameButton.setOnClickListener(this);
        userInfoLL.setOnClickListener(this);
        phoneLL.setOnClickListener(this);
        bioLL.setOnClickListener(this);
        openGalleryLL.setOnClickListener(this);
        deletePhotoLL.setOnClickListener(this);
        translucentLL.setOnClickListener(this);
    }

    private void findViewsById(View view) {
        toolbar = view.findViewById(R.id.dialog_toolbar);
        tvUsername = view.findViewById(R.id.settingsTVUsername);
        tvLogin = view.findViewById(R.id.settingsFrSetupUserNameTV);
        tvBio = view.findViewById(R.id.settingsFrTVBio);
        tvPhone = view.findViewById(R.id.settingsFrTVPhone);
        editUserNameButton = view.findViewById(R.id.settingsFrEditNameButton);
        userInfoLL = view.findViewById(R.id.settingsFrUsername);
        phoneLL = view.findViewById(R.id.settingsFrPhoneNumber);
        bioLL = view.findViewById(R.id.settingsFrBio);
        modifierAvatarLL = view.findViewById(R.id.modifierAvatarLL);
        photoLL = view.findViewById(R.id.photoLayout);
        settingsLL = view.findViewById(R.id.settingsFrUserInfo);
        openGalleryLL = view.findViewById(R.id.openGalleryLL);
        userAvatar = view.findViewById(R.id.usesrAvatar);
        deletePhotoLL = view.findViewById(R.id.deleteAvatarLL);
        modifierLL = view.findViewById(R.id.modifierLL);
        translucentLL = view.findViewById(R.id.translucentLL);
        mainEntrails = view.findViewById(R.id.mainEntrails);
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
                up = AnimationUtils.loadAnimation(getContext(), R.anim.layout_up);
                modifierAvatarLL.setVisibility(VISIBLE);
                translucentLL.setVisibility(VISIBLE);
                modifierLL.setAnimation(up);
                modifierLL.setVisibility(VISIBLE);
                setEnabledFalse();


                break;
            case R.id.openGalleryLL:
                Intent photoIntent = new Intent();
                photoIntent.setType("image/*");
                photoIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(photoIntent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.deleteAvatarLL:
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().remove("AVATAR").apply();
                userAvatar.setImageResource(R.drawable.default_avatar);
                down = AnimationUtils.loadAnimation(getContext(), R.anim.layout_down);
                modifierLL.setAnimation(down);
                translucentLL.setVisibility(GONE);
                modifierAvatarLL.setVisibility(GONE);
                setEnabledTrue();
                break;
            case R.id.translucentLL:
                setEnabledTrue();
                modifierAvatarLL.setVisibility(GONE);
                modifierLL.setVisibility(GONE);
                translucentLL.setVisibility(GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                ImageView imageView = getActivity().findViewById(R.id.usesrAvatar);
                imageView.setImageBitmap(bitmap);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                avatarBytes = bos.toByteArray();
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("AVATAR", Base64.encodeToString(avatarBytes, Base64.DEFAULT)).apply();
                setEnabledTrue();
                modifierAvatarLL.setVisibility(GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void setEnabledTrue() {
        toolbar.setEnabled(true);
        tvUsername.setEnabled(true);
        tvLogin.setEnabled(true);
        tvBio.setEnabled(true);
        tvPhone.setEnabled(true);
        editUserNameButton.setEnabled(true);
        userInfoLL.setEnabled(true);
        phoneLL.setEnabled(true);
        bioLL.setEnabled(true);
        photoLL.setEnabled(true);
        settingsLL.setEnabled(true);
        userAvatar.setEnabled(true);
    }

    private void setEnabledFalse() {
        toolbar.setEnabled(false);
        tvUsername.setEnabled(false);
        tvLogin.setEnabled(false);
        tvBio.setEnabled(false);
        tvPhone.setEnabled(false);
        editUserNameButton.setEnabled(false);
        userInfoLL.setEnabled(false);
        phoneLL.setEnabled(false);
        bioLL.setEnabled(false);
        photoLL.setEnabled(false);
        settingsLL.setEnabled(false);
        userAvatar.setEnabled(false);
    }
}


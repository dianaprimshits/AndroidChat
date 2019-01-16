package com.bigsur.AndroidChatWithMaps.UI.settings;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.startScreen.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FiaskoFragment extends Fragment implements View.OnClickListener, AppBarLayout.OnOffsetChangedListener {
    private int imageX;
    private int totalScrollRange;
    private int imageMargin;
    private int toolbarHeight;
    private int imageStartSize;
    private int imageSize;
    private float imageScale;
    private ViewGroup.MarginLayoutParams imageMarginParams;

    AuthenticationManager authManager = AuthenticationManager.getInstance();
    private FloatingActionButton editUserNameButton;
    Toolbar toolbar;
    ConstraintLayout translucentLL;
    ConstraintLayout modifierLL;
    ConstraintLayout openGalleryLL;
    ConstraintLayout deletePhotoLL;
    ConstraintLayout mainEntrails;
    ConstraintLayout settingsLL;
    ConstraintLayout userInfoLL;
    ConstraintLayout phoneLL;
    ConstraintLayout bioLL;
    byte[] avatarBytes;
    Animation up;
    Animation down;
    TextView tvLogin;
    TextView tvPhone;
    TextView tvBio;
    private LinearLayout nameLL;
    private TextView tvName;
    private AppBarLayout appBarLayout;
    private CircleImageView userAvatar;
    ConstraintLayout modifierAvatarLL;
    private int PICK_IMAGE_REQUEST = 1;




    public static FiaskoFragment newInstance() {
        FiaskoFragment fragment = new FiaskoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fiasko, null);
        findViewsById(view);

        imageMarginParams = (ViewGroup.MarginLayoutParams) userAvatar.getLayoutParams();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);


        setViewResources();
        setListeners();

        view.setHorizontalScrollBarEnabled(false);
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.settings_toolbar_menu, menu);
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


    private void setListeners() {
        userAvatar.setOnClickListener(this);
        appBarLayout.addOnOffsetChangedListener(this);
        editUserNameButton.setOnClickListener(this);
        userInfoLL.setOnClickListener(this);
        phoneLL.setOnClickListener(this);
        bioLL.setOnClickListener(this);
        openGalleryLL.setOnClickListener(this);
        deletePhotoLL.setOnClickListener(this);
        translucentLL.setOnClickListener(this);
    }

    private void setViewResources() {
        tvName.setInputType(InputType.TYPE_NULL);

        tvName.setText(authManager.getUsername());
        tvLogin.setText(authManager.getSavedCredentials().getLogin());
        tvPhone.setText(authManager.getPhoneNumber());
        tvBio.setText(authManager.getBio());
        tvName.setHintTextColor(R.color.white);

        byte[] array = Base64.decode(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("AVATAR", "defaultStringIfNothingFound"), Base64.DEFAULT);
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getString("AVATAR", "defaultStringIfNothingFound").equals("defaultStringIfNothingFound")){
            userAvatar.setImageResource(R.drawable.default_avatar);
        } else {
            userAvatar.setImageBitmap(BitmapFactory.decodeByteArray(array, 0, array.length));
        }

    }


    private void findViewsById(View view) {
        toolbar = view.findViewById(R.id.toolbar);
        editUserNameButton = view.findViewById(R.id.settingsFrEditNameButton);
        tvName = view.findViewById(R.id.settingsTVUsername);
        tvLogin = view.findViewById(R.id.settingsFrSetupUserNameTV);
        tvBio = view.findViewById(R.id.settingsFrTVBio);
        tvPhone = view.findViewById(R.id.settingsFrTVPhone);
        userInfoLL = view.findViewById(R.id.settingsFrUsername);
        phoneLL = view.findViewById(R.id.settingsFrPhoneNumber);
        bioLL = view.findViewById(R.id.settingsFrBio);
        settingsLL = view.findViewById(R.id.settingsFrUserInfo);
        openGalleryLL = view.findViewById(R.id.openGalleryLL);
        deletePhotoLL = view.findViewById(R.id.deleteAvatarLL);
        modifierLL = view.findViewById(R.id.modifierLL);
        translucentLL = view.findViewById(R.id.translucentLL);
        mainEntrails = view.findViewById(R.id.mainEntrails);
        nameLL = view.findViewById(R.id.name);
        userAvatar = view.findViewById(R.id.usesrAvatar);
        appBarLayout = view.findViewById(R.id.appbar);
        modifierAvatarLL = view.findViewById(R.id.modifierAvatarLL);
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
    public void onOffsetChanged(AppBarLayout appBarLayout, int offsetValue) {
        float offset = offsetValue;
        totalScrollRange = appBarLayout.getTotalScrollRange();
        imageMargin = imageMarginParams.topMargin;

        toolbarHeight = toolbar.getHeight();
        imageStartSize = userAvatar.getHeight();

        imageScale = offset / totalScrollRange + 1;
        imageSize = (int) (imageStartSize * imageScale);
        imageX = imageMargin - imageSize + (int) (imageSize * imageScale);

        if (imageSize > toolbarHeight * 0.7) {
            nameLL.setPadding(imageSize, 0, 0, 0);
            userAvatar.setX(imageX);
            userAvatar.setScaleX(imageScale);
            userAvatar.setScaleY(imageScale);
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
        tvName.setEnabled(true);
        tvLogin.setEnabled(true);
        tvBio.setEnabled(true);
        tvPhone.setEnabled(true);
        editUserNameButton.setEnabled(true);
        userInfoLL.setEnabled(true);
        phoneLL.setEnabled(true);
        bioLL.setEnabled(true);
        settingsLL.setEnabled(true);
        userAvatar.setEnabled(true);
    }

    private void setEnabledFalse() {
        toolbar.setEnabled(false);
        tvName.setEnabled(false);
        tvLogin.setEnabled(false);
        tvBio.setEnabled(false);
        tvPhone.setEnabled(false);
        editUserNameButton.setEnabled(false);
        userInfoLL.setEnabled(false);
        phoneLL.setEnabled(false);
        bioLL.setEnabled(false);
        settingsLL.setEnabled(false);
        userAvatar.setEnabled(false);
    }
}

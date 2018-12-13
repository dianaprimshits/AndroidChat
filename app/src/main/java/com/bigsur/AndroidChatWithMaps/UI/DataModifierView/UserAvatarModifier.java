package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.R;

public class UserAvatarModifier extends DataModifier {
    View view;


    public UserAvatarModifier(Context context) {
        super(context);
        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.modifier_avatar, null);
    }

    public UserAvatarModifier(Context context, View view) {
        super(context);
        this.view = view;
    }


    @Override
    public void init1(Context context) {
        ConstraintLayout openPhotoLL = view.findViewById(R.id.openPhotoLL);
        ConstraintLayout openCameraLL = view.findViewById(R.id.openCameraLL);
        ConstraintLayout openGalleryLL = view.findViewById(R.id.openGalleryLL);
        ConstraintLayout deletePhotoLL = view.findViewById(R.id.deleteAvatarLL);

        openCameraLL.setOnClickListener(onClick(context));
        openGalleryLL.setOnClickListener(onClick(context));
        openPhotoLL.setOnClickListener(onClick(context));
        deletePhotoLL.setOnClickListener(onClick(context));
    }



    public View.OnClickListener onClick(Context context) {
        return v -> {
            switch (v.getId()){
                case R.id.openPhotoLL:
                    Toast.makeText(context, "open photo", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.openCameraLL:
                    Toast.makeText(context, "open camera", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.openGalleryLL:
                    Toast.makeText(context, "open gallery", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.deleteAvatarLL:
                    Toast.makeText(context, "delete photo", Toast.LENGTH_SHORT).show();
                    break;
            }
        };
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void init(Context context, Adapter adapter, int position, Activity activity) {
        //do nothig
    }
}

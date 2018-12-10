package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Adapter;

public class ChatModifier extends DataModifier {
    View view;


    @Override
    public void init(final Context context, Adapter adapter, int position, Activity activity) {    }

    @Override
    public View getView() {
        return view;
    }
}

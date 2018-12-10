package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Adapter;

public abstract class DataModifier {

    public abstract void init(final Context context, Adapter adapter, int position, Activity activity);

    public abstract View getView();
}

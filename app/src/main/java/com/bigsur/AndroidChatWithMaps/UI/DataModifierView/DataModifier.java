package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.content.Context;
import android.widget.Adapter;

public interface DataModifier {

    public abstract void init(final Context context, Adapter adapter, int position);
}

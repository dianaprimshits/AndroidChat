package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;

public abstract class DataModifier extends View {
    View view;

    public DataModifier(Context context) {
        super(context);
    }

    public DataModifier(Context context, View view) {
        super(context);
        this.view = view;
    }

    public DataModifier(Context context, @Nullable AttributeSet attrs, View view) {
        super(context, attrs);
        this.view = view;
    }

    public DataModifier(Context context, @Nullable AttributeSet attrs, int defStyleAttr, View view) {
        super(context, attrs, defStyleAttr);
        this.view = view;
    }

    public DataModifier(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes, View view) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.view = view;
    }

    public abstract void init(final Context context, Adapter adapter, int position, Activity activity);

    public abstract void init1(Context context);

    public abstract View getView();
}

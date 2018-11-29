package com.bigsur.AndroidChatWithMaps.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.chats.DialogActivity;


public class DataWithIconListview extends ListView {
    public DataWithIconListview(Context context) {
        super(context);
    }

    public DataWithIconListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataWithIconListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DataWithIconListview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void init(final BaseAdapter adapter, final String comingFrom, final Activity activity) {
        this.setAdapter(adapter);


        this.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dialogIntent = new Intent(activity, DialogActivity.class );
                DataWithIcon data = (DataWithIcon) adapter.getItem(position);
                dialogIntent.putExtra("name", data.getName());

                dialogIntent.putExtra("id", data.getId());
                dialogIntent.putExtra("coming from", comingFrom);
                activity.startActivity(dialogIntent);
            }
        });
    }
}

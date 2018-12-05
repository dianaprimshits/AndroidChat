package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

public class ContactsModifier implements DataModifier {
    public View view;


    public ContactsModifier(Context context) {
        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.modify_contact, null);
    }


    @Override
    public void init(final Context context, Adapter adapter, int position) {

        LinearLayout renameLL = (LinearLayout) view.findViewById(R.id.renameContactLL);
        LinearLayout deleteLL = (LinearLayout) view.findViewById(R.id.deleteContactLL);

        final DataWithIcon data = (DataWithIcon) adapter.getItem(position);

        renameLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "rename" + data.getName(), Toast.LENGTH_LONG).show();
            }
        });

        deleteLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
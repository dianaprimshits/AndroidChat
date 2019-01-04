package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.RenameContactActivity;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

public class ContactsModifier extends DataModifier {
    public View view;


    public ContactsModifier(Context context) {
        super(context);
        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.modify_contact, null);
    }


    @Override
       public void init(final Context context, Adapter adapter, int position, final Activity activity) {

        LinearLayout renameLL =  view.findViewById(R.id.renameContactLL);
        LinearLayout deleteLL =  view.findViewById(R.id.deleteContactLL);

        final DataWithIcon data = (DataWithIcon) adapter.getItem(position);

        renameLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent renameIntent = new Intent(activity, RenameContactActivity.class);
                renameIntent.putExtra("id", data.getId());
                renameIntent.putExtra("name", data.getName());
                renameIntent.putExtra("comingFrom", "contacts");
                context.startActivity(renameIntent);
            }
        });

        deleteLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataWithIconManager dataWithIconManager = ViewableContactManager.getInstance();
                dataWithIconManager.delete(data.getId());
                deleteLL.removeView(view);
            }
        });
    }

    @Override
    public void init1(Context context) {
        //do nothing
    }

    public View getView() {
        return view;
    }

}
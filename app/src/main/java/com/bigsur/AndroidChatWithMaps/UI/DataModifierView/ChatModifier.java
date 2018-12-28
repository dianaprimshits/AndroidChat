package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableChat.ViewableChatManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats.RenameChatActivity;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

public class ChatModifier extends DataModifier {
    View view;

    public ChatModifier(Context context) {
        super(context);
        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.modify_chat, null);
    }


    @Override
    public void init(final Context context, Adapter adapter, int position, final Activity activity) {

        LinearLayout renameLL =  view.findViewById(R.id.renameChatLL);
        LinearLayout deleteLL =  view.findViewById(R.id.deleteChatLL);

        final DataWithIcon data = (DataWithIcon) adapter.getItem(position);

        renameLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent renameIntent = new Intent(activity, RenameChatActivity.class);
                renameIntent.putExtra("id", data.getId());
                renameIntent.putExtra("name", data.getName());
                renameIntent.putExtra("comingFrom", "chatRooms");
                context.startActivity(renameIntent);

            }
        });

        deleteLL.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DataWithIconManager dataWithIconManager = ViewableChatManager.getInstance();
                dataWithIconManager.delete(data.getId());
            }
        });
    }

    @Override
    public void init1(Context context) {

    }

    @Override
    public View getView() {
        return view;
    }
}

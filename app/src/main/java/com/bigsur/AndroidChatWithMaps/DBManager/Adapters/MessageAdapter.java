package com.bigsur.AndroidChatWithMaps.DBManager.Adapters;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Messages;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {

    Context ctx;
    ArrayList<Messages> messages;
    LayoutInflater lInflater;
    AuthenticationManager authManager = AuthenticationManager.getInstance();

    public MessageAdapter(Context ctx, ArrayList<Messages> messages) {
        this.ctx = ctx;
        this.messages = messages;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return messages.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.message_list, parent, false);
        }

        Messages message = getMessages(position);
        ((TextView) view.findViewById(R.id.messageTextTv)).setText(message.getMessage());
        ((TextView) view.findViewById(R.id.messageDateTv)).setText(message.getDate() + "");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (message.getMessageFrom() == authManager.getUserId()) {
            params.gravity = Gravity.RIGHT;
            view.setLayoutParams(params);
        }
        params.gravity = Gravity.RIGHT;
        view.setLayoutParams(params);

        return view;
    }

    Messages getMessages(int position) {
        return ((Messages) getItem(position));
    }
}

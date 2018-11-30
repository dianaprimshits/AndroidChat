package com.bigsur.AndroidChatWithMaps.DBManager.Adapters;


import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.AuthManager.AuthenticationManager;
import com.bigsur.AndroidChatWithMaps.DB.Messages.Messages;
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
        ViewHolder viewHolder;
        View view = convertView;
        Messages message = getMessages(position);

        if (view == null) {
            view = lInflater.inflate(R.layout.message_list_right, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        setAlignment(viewHolder, message);

        viewHolder.messageTextTV.setText(message.getMessage());
        viewHolder.messageDateTV.setText(message.getDate() + "");
        return view;
    }

    Messages getMessages(int position) {
        return ((Messages) getItem(position));
    }


    private class ViewHolder {
        TextView messageTextTV;
        TextView messageDateTV;
        ConstraintLayout messageBlock;
        ConstraintLayout contentWithBG;

        ViewHolder(View view){
            messageTextTV = (TextView) view.findViewById(R.id.messageTextTv);
            messageDateTV = (TextView) view.findViewById(R.id.messageDateTv);
            messageBlock = (ConstraintLayout) view.findViewById(R.id.messageBlock);
            contentWithBG = (ConstraintLayout) view.findViewById(R.id.contentWithBG);
        }
    }


    private void setAlignment(ViewHolder viewHolder, Messages message) {
        if (message.getMessageFrom() == authManager.getUserId()) {
            ConstraintSet set = new ConstraintSet();
            set.clone(viewHolder.messageBlock);
            set.connect(viewHolder.contentWithBG.getId(), ConstraintSet.RIGHT, viewHolder.messageBlock.getId(), ConstraintSet.RIGHT, 10);
            set.applyTo(viewHolder.messageBlock);

        } else {
            viewHolder.contentWithBG.setBackgroundResource(R.drawable.rounded_foreign_message_color);
            ConstraintSet set = new ConstraintSet();
            set.clone(viewHolder.messageBlock);
            set.connect(viewHolder.contentWithBG.getId(), ConstraintSet.LEFT, viewHolder.messageBlock.getId(), ConstraintSet.LEFT, 10);
            set.applyTo(viewHolder.messageBlock);

        }
    }

}

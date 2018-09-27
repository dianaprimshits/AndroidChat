package com.bigsur.AndroidChatWithMaps.DBManager.Adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;

public class AdapterForChatRooms extends BaseAdapter {
    Context context;
    private ArrayList<ChatRooms> data;
    LayoutInflater lInflater;

    public AdapterForChatRooms(Context context, ArrayList<DataFromDB> chatRooms) {

        ArrayList<ChatRooms> chats = new ArrayList<>();
        for (int i = 0; i < chatRooms.size(); i++) {
            chats.add((ChatRooms) chatRooms.get(i).getData());
        }


        this.data = new ArrayList<>();
        this.data.addAll(chats);


        Log.d("!!!!LOG!!!!", "AdapterForChatRooms: " + data.toString());
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public DataWithIcon getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = lInflater.inflate(R.layout.chat_list, parent, false);
        }

        ImageView avatar = (ImageView) view.findViewById(R.id.chatAvatar);
        TextView name = (TextView) view.findViewById(R.id.chatName);
        TextView subname = (TextView) view.findViewById(R.id.chatLastMessage);
        TextView date = (TextView) view.findViewById(R.id.chatLastMessageDate);

        DataWithIcon selectedItem = getItem(position);
        name.setText(selectedItem.getName().toString());
        subname.setText(selectedItem.getSubname().toString());
        avatar.setImageResource(selectedItem.getAvatar());
        date.setText(selectedItem.getDate());
        return view;

    }
}

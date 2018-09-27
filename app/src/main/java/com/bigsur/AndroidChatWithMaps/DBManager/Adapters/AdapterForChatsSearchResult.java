package com.bigsur.AndroidChatWithMaps.DBManager.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.ChatRooms;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;

public class AdapterForChatsSearchResult extends BaseAdapter implements Filterable {
    Context context;
    private ArrayList<DataWithIcon> dataForSearch;
    LayoutInflater lInflater;
    ArrayList<DataWithIcon> dataAfterSerach;

    public AdapterForChatsSearchResult(Context context, ArrayList<DataFromDB> contacts, ArrayList<DataFromDB> chatRooms) {
        ArrayList<DataWithIcon> newList = new ArrayList<>();

        if (chatRooms.size() == 0) {
            for (int i = 0; i < contacts.size() + chatRooms.size(); i++) {
                newList.add((Contacts) contacts.get(i).getData());
            }
        } else {
            for (int i = 0; i < contacts.size() + chatRooms.size(); i++) {
                newList.add((Contacts) contacts.get(i).getData());
                newList.add((ChatRooms) chatRooms.get(i).getData());
            }
        }

        this.dataForSearch = new ArrayList<>();
        this.dataAfterSerach = new ArrayList<>();

        this.dataForSearch.addAll(newList);
        this.dataAfterSerach.addAll(dataForSearch);
        this.context = context;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataAfterSerach.size();
    }

    @Override
    public DataWithIcon getItem(int position) {
        return dataAfterSerach.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataAfterSerach.get(position).getId();
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
        name.setText(selectedItem.getName());
        subname.setText(selectedItem.getSubname());
//        avatar.setImageResource(selectedItem.getAvatar());
//        date.setText(selectedItem.getDate());
        return view;
    }
/*
    public void filter(String text){
       // dataCopy.clear();
        if(text.isEmpty()) {
            this.dataAfterSerach.addAll(dataForSearch);
        } else {
            String lowerCaseText = text.toLowerCase();
            for(DataWithIcon dataWithIcon: dataForSearch) {
                if(dataWithIcon.getName().toLowerCase().contains(lowerCaseText)) {
                    dataAfterSerach.add(dataWithIcon);
                }
            }
        }

        notifyDataSetChanged();
    }
*/


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                dataAfterSerach.clear();
                if (constraint == null || constraint.length() == 0) {
                    result.values = dataForSearch;
                    result.count = dataForSearch.size();
                } else {
                    for (DataWithIcon z : dataForSearch) {
                        if (z.getName().toLowerCase().contains(constraint))
                            dataAfterSerach.add(z);
                    }
                    result.values = dataAfterSerach;
                    result.count = dataAfterSerach.size();
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               // dataForSearch = (ArrayList<DataWithIcon>) results.values;

                notifyDataSetChanged();
            }
        };
        return filter;
    }
}

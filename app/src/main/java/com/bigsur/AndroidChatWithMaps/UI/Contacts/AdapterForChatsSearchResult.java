package com.bigsur.AndroidChatWithMaps.UI.Contacts;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.ArrayList;

public class AdapterForChatsSearchResult extends BaseAdapter implements Filterable {
    Context context;
    private ArrayList<DataWithIcon> dataForSearch;
    ArrayList<DataWithIcon> dataAfterSerach;

    public AdapterForChatsSearchResult(Context context, ArrayList<DataWithIcon> contacts) {
        ArrayList<DataWithIcon> newList = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            newList.add(contacts.get(i));
        }


        this.dataForSearch = new ArrayList<>();
        this.dataAfterSerach = new ArrayList<>();

        this.dataForSearch.addAll(newList);
        this.dataAfterSerach.addAll(dataForSearch);
        this.context = context;
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


        DataWithIcon selectedItem = getItem(position);
        name.setText(selectedItem.getName());
        subname.setText(selectedItem.getSubname());
//      avatar.setImageResource(selectedItem.getAvatar());
        return view;
    }


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

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // dataForSearch = (ArrayList<DataWithIcon>) results.values;

                notifyDataSetChanged();
            }
        };
        return filter;
    }
}

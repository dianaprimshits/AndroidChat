package com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
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
import java.util.Collections;
import java.util.Comparator;

public class AdapterForGroupAdd extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<DataWithIcon> contactList;
    ArrayList<DataWithIcon> filteredList;
    ValueFilter valueFilter;

    public AdapterForGroupAdd(Context context, ArrayList<DataWithIcon> data) {
        filteredList = new ArrayList<>();
        contactList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            contactList.add(data.get(i));
        }
        Collections.sort(contactList, ALPHABETICAL_ORDER);
        filteredList.addAll(contactList);
        this.context = context;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public DataWithIcon getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contactList.get(position).getId();
    }


    private class ViewHolder {
        TextView firstLaterContactName;
        TextView contactName;
        TextView contactPhone;
        ImageView contactAvatar;
        ConstraintLayout checkbox;

        ViewHolder(View view) {
            firstLaterContactName = view.findViewById(R.id.checkableFirstLaterContactName);
            contactAvatar = view.findViewById(R.id.checkableContactAvatar);
            contactName = view.findViewById(R.id.checkableChatAddActContactName);
            contactPhone = view.findViewById(R.id.checkableContactPhoneNumber);
            checkbox = view.findViewById(R.id.checkableBtLL);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = lInflater.inflate(R.layout.add_chat_listview_item_with_cherbox, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DataWithIcon contact = getItem(position);
        viewHolder.contactName.setText(contact.getName());
        viewHolder.contactPhone.setText(contact.getSubname());
        // viewHolder.contactAvatar.setImageResource(contact.getAvatar());
        viewHolder.firstLaterContactName.setText(contact.getName().substring(0, 1).toUpperCase());
        return view;
    }

    public void setCheckBoxVisibility(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder.checkbox.getVisibility() == View.GONE) {
            viewHolder.checkbox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checkbox.setVisibility(View.GONE);
        }
    }

    public int getCheckBoxVisibility(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        return viewHolder.checkbox.getVisibility();
    }


    private static Comparator<DataWithIcon> ALPHABETICAL_ORDER = new Comparator<DataWithIcon>() {
        @Override
        public int compare(DataWithIcon o1, DataWithIcon o2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
            return (res != 0) ? res : o1.getName().compareTo(o2.getName());
        }
    };


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<DataWithIcon> filterList = new ArrayList<>();
                for (int i = 0; i < filteredList.size(); i++) {
                    if ((filteredList.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(filteredList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList = (ArrayList<DataWithIcon>) results.values;
            notifyDataSetChanged();
        }
    }
}
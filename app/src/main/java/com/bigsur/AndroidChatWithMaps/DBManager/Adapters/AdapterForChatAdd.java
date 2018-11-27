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

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class AdapterForChatAdd extends BaseAdapter implements Filterable {
    Context context;
    ArrayList<Contacts> contactList;
    ArrayList<Contacts> filteredList;
    ValueFilter valueFilter;

    public AdapterForChatAdd(Context context, ArrayList<DataFromDB> contacts) {
        filteredList = new ArrayList<>();
        contactList = new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            contactList.add((Contacts) contacts.get(i).getData());
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
    public Contacts getItem(int position) {
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


        ViewHolder(View view) {
            firstLaterContactName = (TextView) view.findViewById(R.id.firstLaterContactName);
            contactAvatar = (ImageView) view.findViewById(R.id.contactAvatar);
            contactName = (TextView) view.findViewById(R.id.chatAddActContactName);
            contactPhone = (TextView) view.findViewById(R.id.contactPhoneNumber);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = lInflater.inflate(R.layout.add_chat_listview_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Contacts contact = getItem(position);
        viewHolder.contactName.setText(contact.getName());
        viewHolder.contactPhone.setText(contact.getPhoneNumber());
        // viewHolder.contactAvatar.setImageResource(contact.getAvatar());
        viewHolder.firstLaterContactName.setText(contact.getName().substring(0, 1).toUpperCase());

        return view;
    }

    private static Comparator<Contacts> ALPHABETICAL_ORDER = new Comparator<Contacts>() {
        @Override
        public int compare(Contacts o1, Contacts o2) {
            int res = String.CASE_INSENSITIVE_ORDER.compare(o1.getContactName(), o2.getContactName());
            return (res != 0) ? res : o1.getContactName().compareTo(o2.getContactName());
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
                ArrayList<Contacts> filterList = new ArrayList<>();
                for (int i = 0; i < filteredList.size(); i++) {
                    if ((filteredList.get(i).getContactName().toUpperCase()).contains(constraint.toString().toUpperCase())) {
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
            contactList = (ArrayList<Contacts>) results.values;
            notifyDataSetChanged();
        }
    }
}

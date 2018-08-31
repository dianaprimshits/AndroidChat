package com.bigsur.AndroidChatWithMaps.DBManager;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<Contacts> {

    private final Activity context;
    private ArrayList<Contacts> contacts;

    public CustomAdapter(Activity context, ArrayList<Contacts> contacts) {
        super(context, R.layout.listview, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.contact_list, parent, false);
        TextView contactName = (TextView) rowView.findViewById(R.id.contactName);
        TextView contactPhoneNumber = (TextView) rowView.findViewById(R.id.contactPhoneNumber);
        contactName.setText(contacts.get(position).getName());
        contactPhoneNumber.setText(contacts.get(position).getPhone_number());
        return rowView;
    }




















/*
    public CustomAdapter(Context context, ArrayList<Contacts> contacts) {
        super(context, R.layout.listview, contacts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contacts contacts = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.listview, null);
        }
        ((TextView) convertView.findViewById(R.id.contactName))
                .setText(contacts.getName());
        ((TextView) convertView.findViewById(R.id.contactPhoneNumber))
                .setText(contacts.getPhone_number());
        return convertView;
    }*/
}

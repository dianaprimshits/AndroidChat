package com.bigsur.AndroidChatWithMaps.DBManager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DBManager.DataFromDB;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;


public class CustomAdapterForContacts extends ArrayAdapter<DataFromDB> {

    Context context;
    private ArrayList<DataFromDB> contacts;
    LayoutInflater lInflater;

    public CustomAdapterForContacts(Context context, ArrayList<DataFromDB> contacts) {
        super(context, R.layout.contact_list, contacts);
        this.context = context;
        this.contacts = contacts;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = lInflater.inflate(R.layout.contact_list, parent, false);
        TextView contactName = (TextView) rowView.findViewById(R.id.contactName);
        TextView contactPhoneNumber = (TextView) rowView.findViewById(R.id.contactPhoneNumber);
        Contacts selectedItem = (Contacts) getItem(position).getData();
        contactName.setText(selectedItem.getName());
        contactPhoneNumber.setText(selectedItem.getPhone_number());
        return rowView;

    }

}

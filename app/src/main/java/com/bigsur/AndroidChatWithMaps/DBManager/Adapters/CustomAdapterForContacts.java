package com.bigsur.AndroidChatWithMaps.DBManager.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DBManager.Entities.Contacts;
import com.bigsur.AndroidChatWithMaps.DBManager.Entities.DataFromDB;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;


public class CustomAdapterForContacts extends ArrayAdapter<DataFromDB> {

    Context context;
    private ArrayList<DataFromDB> data;
    LayoutInflater lInflater;

    public CustomAdapterForContacts(Context context, ArrayList<DataFromDB> data) {
        super(context, R.layout.contact_list, data);
        this.context = context;
        this.data = data;
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
        contactName.setText(selectedItem.getContactName());
        contactPhoneNumber.setText(selectedItem.getPhone_number());
        return rowView;

    }

}

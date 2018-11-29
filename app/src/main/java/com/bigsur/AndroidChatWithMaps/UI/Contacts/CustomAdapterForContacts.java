package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.R;

import java.util.ArrayList;


public class CustomAdapterForContacts extends ArrayAdapter<DataWithIcon> {

    Context context;
    private ArrayList<DataWithIcon> data;
    LayoutInflater lInflater;

    public CustomAdapterForContacts(Context context, ArrayList<DataWithIcon> data) {
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
        DataWithIcon selectedItem = (DataWithIcon) getItem(position);
        contactName.setText(selectedItem.getName());
        contactPhoneNumber.setText(selectedItem.getSubname());
        return rowView;

    }

}

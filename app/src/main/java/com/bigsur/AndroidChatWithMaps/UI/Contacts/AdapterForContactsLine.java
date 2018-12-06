package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.ArrayList;

public class AdapterForContactsLine extends BaseAdapter {
    Context context;
    ArrayList<DataWithIcon> data;
    DataWithIconManager manager;
    LayoutInflater lInflater;

    public AdapterForContactsLine(Context context, DataWithIconManager manager) {
        super();
        this.data = manager.getAll();
        this.manager = manager;
        this.context = context;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        this.data = manager.getAll();
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }


    private class ViewHolder {
        TextView title;
        ImageView avatar;

        ViewHolder(View view){
            //avatar = (ImageView)rowView.findViewById(R.id.horizontalLineAvatar);
            title = (TextView)view.findViewById(R.id.horizontalLineContact);
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        DataWithIcon dataWithIcon = (DataWithIcon)getItem(position);

        if (view == null) {
            view = lInflater.inflate(R.layout.horizontal_contact_listview_item, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // viewHolder.avatar.setImageResource(selectedItem.getAvatar());
        viewHolder.title.setText(dataWithIcon.getName());

        return view;
    }
}

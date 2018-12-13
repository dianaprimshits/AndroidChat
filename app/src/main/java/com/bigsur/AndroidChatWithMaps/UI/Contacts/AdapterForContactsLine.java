package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.util.ArrayList;

public class AdapterForContactsLine extends RecyclerView.Adapter {
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.horizontal_contact_listview_item, parent, false);
        return new MyItemView(itemView);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder _holder, final int position) {
        MyItemView holder = (MyItemView)_holder;
        DataWithIcon mItem = data.get(position);
        holder.textViewTitle.setText(mItem.getName());
  //      holder.imageView.setImageResource(mItem.getAvatar());
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    public DataWithIcon getItem(int position) {
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyItemView extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageButton imageView;

        MyItemView(View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.horizontalLineContact);
           // imageView = view.findViewById(R.id.horizontalLineAvatar);
        }
    }
}

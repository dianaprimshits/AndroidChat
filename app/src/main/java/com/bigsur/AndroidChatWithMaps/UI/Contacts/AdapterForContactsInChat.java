package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterForContactsInChat extends BaseAdapter {

    Context context;
    ArrayList<DataWithIcon> data;
    DataWithIconManager manager;
    LayoutInflater lInflater;

    public AdapterForContactsInChat(Context context, DataWithIconManager manager, ArrayList<Integer> ids) {
        super();
        data = new ArrayList<>();
        for(int id: ids) {
            data.add(manager.getById(id));
        }
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
        TextView subTitle;
        TextView extraTitle;
        ImageView avatar;

        ViewHolder(View view){
            //avatar = (ImageView)rowView.findViewById(R.id.dataWithIconAvatar);
            title = view.findViewById(R.id.dataWithIconTitle);
            subTitle = view.findViewById(R.id.dataWithIconSubTitle);
            extraTitle = view.findViewById(R.id.dataWithIconExtraTitle);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        DataWithIcon dataWithIcon = (DataWithIcon)getItem(position);

        if (view == null) {
            view = lInflater.inflate(R.layout.contact_list, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // avatar.setImageResource(selectedItem.getAvatar());
        viewHolder.title.setText(dataWithIcon.getName());
        viewHolder.subTitle.setText(dataWithIcon.getSubname());

        if (dataWithIcon.getExtraTitle() == null) {
            viewHolder.extraTitle.setText("");
        } else {
            viewHolder.extraTitle.setText(new SimpleDateFormat("HH:mm").format(dataWithIcon.getExtraTitle()));
        }
        return view;
    }

}

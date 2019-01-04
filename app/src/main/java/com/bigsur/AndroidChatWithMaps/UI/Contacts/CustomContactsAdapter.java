package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigsur.AndroidChatWithMaps.ContactsDataManager;
import com.bigsur.AndroidChatWithMaps.ImageConverter;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class CustomContactsAdapter extends BaseAdapter {

    Context context;
    ArrayList<DataWithIcon> data;
    ContactsDataManager manager;
    LayoutInflater lInflater;

    public CustomContactsAdapter(Context context, ContactsDataManager manager) {
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
        TextView subTitle;
        TextView extraTitle;
        ImageView extraTitleIcon;
        ImageView avatar;

        ViewHolder(View view){
            avatar = view.findViewById(R.id.dataWithIconAvatar);
            title = view.findViewById(R.id.dataWithIconTitle);
            subTitle = view.findViewById(R.id.dataWithIconSubTitle);
            extraTitle = view.findViewById(R.id.dataWithIconExtraTitle);
            extraTitleIcon = view.findViewById(R.id.dataWithIconExtraTitleIcon);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        DataWithIcon dataWithIcon = (DataWithIcon)getItem(position);
        Log.d("!!!LOG!!!ADAPTER", "getView: " +dataWithIcon);

        if (view == null) {
            view = lInflater.inflate(R.layout.contact_list, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        if(dataWithIcon.getAvatar() != null) {
            Bitmap photo = ImageConverter.convertToBitmap(dataWithIcon.getAvatar());
            viewHolder.avatar.setImageBitmap(photo);
        } else {
            viewHolder.avatar.setImageResource(R.drawable.ic_launcher_round);
        }
        viewHolder.title.setText(dataWithIcon.getName());
        viewHolder.subTitle.setText(dataWithIcon.getSubname());


        if (dataWithIcon.getExtraTitle() == null) {
            viewHolder.extraTitle.setText("");
        } else {
            viewHolder.extraTitle.setText(new SimpleDateFormat("HH:mm").format(dataWithIcon.getExtraTitle()));
        }

        if(dataWithIcon.getExtraTitleIcon() != null) {
            viewHolder.extraTitleIcon.setBackgroundResource(R.drawable.check);
        }

        return view;
    }

}

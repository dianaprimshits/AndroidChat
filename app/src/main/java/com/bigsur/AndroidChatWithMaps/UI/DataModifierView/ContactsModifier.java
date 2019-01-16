package com.bigsur.AndroidChatWithMaps.UI.DataModifierView;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.ItemContactsFragment;
import com.bigsur.AndroidChatWithMaps.UI.Contacts.RenameContactActivity;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconManager;

public class ContactsModifier extends DataModifier {
    public View view;
    Runnable onClosed;

    public ContactsModifier(Context context) {
        super(context);
        LayoutInflater li = LayoutInflater.from(context);
        view = li.inflate(R.layout.modify_contact, null);
    }

    public void setOnClosedBehavior(Runnable onClosed) {
        this.onClosed = onClosed;
    }


    @Override
    public void init(final Context context, Adapter adapter, int position, final Activity activity) {

        LinearLayout renameLL = view.findViewById(R.id.renameContactLL);
        LinearLayout deleteLL = view.findViewById(R.id.deleteContactLL);

        DataWithIcon data = (DataWithIcon) adapter.getItem(position);
        renameLL.setOnClickListener(onClick(activity, data, context));
        deleteLL.setOnClickListener(onClick(activity, data, context));
    }

    private OnClickListener onClick(Activity activity, DataWithIcon data, Context context) {
        return v -> {
            switch (v.getId()) {
                case R.id.renameContactLL:
                    Intent renameIntent = new Intent(activity, RenameContactActivity.class);
                    renameIntent.putExtra("id", data.getId());
                    renameIntent.putExtra("name", data.getName());
                    renameIntent.putExtra("comingFrom", "contacts");
                    context.startActivity(renameIntent);
                    onClosed.run();
                    break;
                case R.id.deleteContactLL:
                    DataWithIconManager dataWithIconManager = ViewableContactManager.getInstance();
                    dataWithIconManager.delete(data.getId(),
                            () -> Toast.makeText(context, "Contact deleted", Toast.LENGTH_SHORT).show(),
                            () -> Toast.makeText(context, "Contact can't be deleted", Toast.LENGTH_SHORT).show());
                    Fragment contactsFragment = ItemContactsFragment.newInstance();
                    FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, contactsFragment);
                    transaction.commit();
                    onClosed.run();
                    break;
            }
        };
    }

    @Override
    public void init1(Context context) {
        //do nothing
    }

    public View getView() {
        return view;
    }

}
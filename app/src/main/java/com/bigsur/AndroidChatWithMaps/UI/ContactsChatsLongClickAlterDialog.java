package com.bigsur.AndroidChatWithMaps.UI;


import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIcon;
import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;

public class ContactsChatsLongClickAlterDialog extends AlertDialog implements DialogInterface {
    public ContactsChatsLongClickAlterDialog(@NonNull Context context) {
        super(context);
    }

    protected ContactsChatsLongClickAlterDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ContactsChatsLongClickAlterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(Context context, View view, final DataWithIconManager manager, final Adapter adapter, final int position) {
        android.app.AlertDialog.Builder mDialogBuilder = new android.app.AlertDialog.Builder(context);
        mDialogBuilder.setView(view);
        mDialogBuilder
                .setCancelable(true)
                .setPositiveButton("cancel",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DataWithIcon data = (DataWithIcon) adapter.getItem(position);
                                manager.delete(data.getId());
                            }
                        });
        android.app.AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();

        final Button positiveButton = alertDialog.getButton(ContactsChatsLongClickAlterDialog.BUTTON_POSITIVE);
        final Button negativeButton = alertDialog.getButton(ContactsChatsLongClickAlterDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams buttonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
        buttonLL.weight = 1;
        buttonLL.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(buttonLL);
        negativeButton.setLayoutParams(buttonLL);
    }
}

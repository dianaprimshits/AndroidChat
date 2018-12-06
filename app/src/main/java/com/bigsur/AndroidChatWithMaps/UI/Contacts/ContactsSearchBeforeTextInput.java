package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview;


public class ContactsSearchBeforeTextInput extends Fragment {
    DataWithIconListview lvMain;
    DataWithIconManager dataManager = ViewableContactManager.getInstance();
    AdapterForContactsLine adapter;

    public static ContactsSearchBeforeTextInput newInstance() {
        ContactsSearchBeforeTextInput fragment = new ContactsSearchBeforeTextInput();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_search_before_text_input_fragment, container, false);
        findViewsById(view);
        adapter = new AdapterForContactsLine(getContext(), dataManager);
        lvMain.init(dataManager, adapter, "contacts", getActivity());
        lvMain.setAdapter(adapter);

        return view;
    }

    private void findViewsById(View view) {
        lvMain = (DataWithIconListview) view.findViewById(R.id.lvMain);
    }
}

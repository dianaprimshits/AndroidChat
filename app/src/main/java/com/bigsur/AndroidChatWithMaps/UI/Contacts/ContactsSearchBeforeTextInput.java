package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;


public class ContactsSearchBeforeTextInput extends Fragment {
    RecyclerView lvMain;
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
        lvMain.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        lvMain.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        return view;
    }

    private void findViewsById(View view) {
        lvMain =  view.findViewById(R.id.my_recycler_view);
    }
}
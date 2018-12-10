package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigsur.AndroidChatWithMaps.DB.DataWithIconManager;
import com.bigsur.AndroidChatWithMaps.Domain.ViewableContact.ViewableContactManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.DataWithIconListview.DataWithIconListview;


public class ContactsSearchAfterTextInput extends Fragment {
    AdapterForChatsSearchResult searchAdapter;
    DataWithIconListview lvMain;
    DataWithIconManager dataManager = ViewableContactManager.getInstance();
    String searchText = "";


    public static ContactsSearchAfterTextInput newInstance() {
        ContactsSearchAfterTextInput fragment = new ContactsSearchAfterTextInput();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_search_after_text_input_fragment, container, false);
        findViewsById(view);
        searchAdapter = new AdapterForChatsSearchResult(getContext(), dataManager.getAll());
        lvMain.init(dataManager, new CustomContactsAdapter(getContext(), dataManager), "contacts", getActivity());
        render();
        return view;
    }


    private void findViewsById(View view) {
        lvMain = (DataWithIconListview) view.findViewById(R.id.lvMain);
    }

    private void render() {
        if (lvMain == null) {
            return;
        }

        lvMain.setAdapter(searchAdapter);
        searchAdapter.getFilter().filter(searchText);
    }

    public void setSearchTextChange(String newText) {
        if (newText != searchText) {
            searchText = newText;
            render();
        }
    }

}
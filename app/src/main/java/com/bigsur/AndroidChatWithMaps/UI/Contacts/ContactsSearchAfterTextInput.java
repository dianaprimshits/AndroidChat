package com.bigsur.AndroidChatWithMaps.UI.Contacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigsur.AndroidChatWithMaps.DB.Contacts.SQLiteContactsManager;
import com.bigsur.AndroidChatWithMaps.R;
import com.bigsur.AndroidChatWithMaps.UI.ChatRooms.chats.DialogActivity;


public class ContactsSearchAfterTextInput extends Fragment {
    AdapterForChatsSearchResult searchAdapter;
    ListView lvMain;
    SQLiteContactsManager contactsManager = new SQLiteContactsManager();
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
        render();
        return view;
    }


    private void findViewsById(View view) {
        lvMain = (ListView) view.findViewById(R.id.lvMain);
    }

    private void render() {
        if (lvMain == null) {
            return;
        }

        if (searchAdapter == null) {
            searchAdapter = new AdapterForChatsSearchResult(getContext(), contactsManager.getAll());
            lvMain.setAdapter(searchAdapter);
        }

        searchAdapter.getFilter().filter(searchText);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent dialogIntent = new Intent(getActivity(), DialogActivity.class);
                dialogIntent.putExtra("name", searchAdapter.getItem(position).getName());

                Log.d("!!!!!!!!!LOL!!!!!!!", "onItemClick:" + searchAdapter.getItem(position).getClass().getSimpleName());
                Log.d("!!!!!!!!!LOL!!!!!!!", "onItemClick:" + searchAdapter.getItem(position).getId());

                dialogIntent.putExtra("id", searchAdapter.getItem(position).getId());

                if (searchAdapter.getItem(position).getClass().getSimpleName().equals("Contacts")) {
                    dialogIntent.putExtra("coming from", "contacts");
                }

                if (searchAdapter.getItem(position).getClass().getSimpleName().equals("ChatRooms")) {
                    dialogIntent.putExtra("coming from", "chatRooms");
                }

                startActivity(dialogIntent);
            }
        });
    }

    public void setSearchTextChange(String newText) {
        if (newText != searchText) {
            searchText = newText;
            render();
        }
    }

}

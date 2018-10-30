package com.bigsur.AndroidChatWithMaps.contacts;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigsur.AndroidChatWithMaps.R;

import androidx.fragment.app.Fragment;


public class ContactsSearchBeforeTextInput extends Fragment {

    public static ContactsSearchBeforeTextInput newInstance() {
        ContactsSearchBeforeTextInput fragment = new ContactsSearchBeforeTextInput();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_search_before_text_input_fragment, null);
    }
}

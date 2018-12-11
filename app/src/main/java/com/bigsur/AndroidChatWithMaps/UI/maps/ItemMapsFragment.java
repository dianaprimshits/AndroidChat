package com.bigsur.AndroidChatWithMaps.UI.maps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigsur.AndroidChatWithMaps.R;
import com.mapbox.mapboxsdk.Mapbox;


public class ItemMapsFragment extends Fragment {

    public static ItemMapsFragment newInstance() {
        ItemMapsFragment fragment = new ItemMapsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        CustomMapView mapView = new CustomMapView(getContext());
        mapView.init(getContext(), savedInstanceState, view);

        return view;
    }
}
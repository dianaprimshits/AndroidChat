package com.bigsur.AndroidChatWithMaps.UI.maps;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bigsur.AndroidChatWithMaps.R;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class CustomMapView extends MapView {
    MapView mapView;

    public MapView getMapView() {
        return mapView;
    }

    public CustomMapView(@NonNull Context context) {
        super(context);
    }

    public CustomMapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomMapView(@NonNull Context context, @Nullable MapboxMapOptions options) {
        super(context, options);
    }

    @Override
    public void getMapAsync(@NonNull OnMapReadyCallback callback) {
        super.getMapAsync(callback);
    }


    public void init(Context context, Bundle savedInstanceState, View view) {
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(new LatLng(53.9111266, 27.454437))
                                .zoom(17)
                                .tilt(45.0)
                                .build()),
                        10000);
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(53.9111266, 27.454437))
                        .title("Heapix")
                        .snippet("Minsk")
                );

                mapboxMap.getUiSettings().setAttributionEnabled(false);
                mapboxMap.getUiSettings().setLogoEnabled(false);
            }
        });
    }


}

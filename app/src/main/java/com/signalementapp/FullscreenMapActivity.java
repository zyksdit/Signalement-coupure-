package com.signalementapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FullscreenMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fullscreen_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Afficher les mêmes marqueurs
        LatLng waterCut = new LatLng(36.8065, 10.1815);
        LatLng electricityCut = new LatLng(36.8500, 10.2500);
        mMap.addMarker(new MarkerOptions().position(waterCut).title("Coupure d'eau"));
        mMap.addMarker(new MarkerOptions().position(electricityCut).title("Coupure d'électricité"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(waterCut, 12));
    }
}
/*
Cannot resolve symbol 'gms'
Cannot resolve symbol 'OnMapReadyCallback'
Cannot resolve symbol 'GoogleMap'
Cannot resolve symbol 'SupportMapFragment'
Cannot resolve method 'getMapAsync(FullscreenMapActivity)'
Cannot resolve symbol 'LatLng'
Cannot resolve method 'addMarker(?)'
Cannot resolve symbol 'MarkerOptions'
Cannot resolve method 'moveCamera(?)'
Cannot resolve symbol 'CameraUpdateFactory'
Cannot resolve symbol 'SupportMapFragment'
'replace(int, androidx.fragment.app.Fragment)' in 'androidx.fragment.app.FragmentTransaction' cannot be applied to '(int, SupportMapFragment)'
Cannot resolve method 'getView()'









 */
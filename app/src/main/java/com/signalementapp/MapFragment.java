package com.signalementapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);

        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.google_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng location = new LatLng(36.8065, 10.1815);
        mMap.addMarker(new MarkerOptions().position(location).title("Exemple de coupure"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f));
    }
}

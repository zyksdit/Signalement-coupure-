package com.signalementapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreerSignalementActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;
    private Marker currentMarker;

    private RadioGroup radioGroupType;
    private EditText editTextDescription;
    private ImageButton buttonAjouterPhoto;
    private Button buttonSoumettre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_signalement);

        // Initialisation des vues
        radioGroupType = findViewById(R.id.radioGroupType);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAjouterPhoto = findViewById(R.id.buttonAjouterPhoto);
        buttonSoumettre = findViewById(R.id.buttonSoumettre);

        // Initialisation carte
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        buttonSoumettre.setOnClickListener(v -> {
            int selectedId = radioGroupType.getCheckedRadioButtonId();
            RadioButton selectedRadio = findViewById(selectedId);
            String type = (selectedRadio != null) ? selectedRadio.getText().toString() : "Non spécifié";
            String description = editTextDescription.getText().toString();
            LatLng position = (currentMarker != null) ? currentMarker.getPosition() : null;

            if (position != null) {
                Toast.makeText(this, "Type: " + type + "\nDescription: " + description +
                        "\nLatitude: " + position.latitude + "\nLongitude: " + position.longitude, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Veuillez sélectionner une localisation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Vérification permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true);

        // Dernière position connue
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        moveMarker(currentLocation);
                    }
                });

        // Marqueur déplaçable
        mMap.setOnMapClickListener(latLng -> moveMarker(latLng));
    }

    private void moveMarker(LatLng latLng) {
        if (currentMarker != null) {
            currentMarker.setPosition(latLng);
        } else {
            currentMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Localisation choisie"));
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap); // relancer la carte avec les permissions
            } else {
                Toast.makeText(this, "Permission localisation refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


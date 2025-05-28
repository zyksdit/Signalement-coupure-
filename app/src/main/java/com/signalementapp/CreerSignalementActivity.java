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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.signalementapp.models.Signalement;
import com.signalementapp.network.ApiClient;
import com.signalementapp.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class CreerSignalementActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private MapView mapView;
    private GoogleMap mMap;
    private Marker currentMarker;
    private FusedLocationProviderClient fusedLocationClient;

    private RadioGroup radioGroupType;
    private EditText editTextDescription;
    private ImageButton buttonAjouterPhoto;
    private Button buttonSoumettre;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_signalement);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        radioGroupType = findViewById(R.id.radioGroupType);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonAjouterPhoto = findViewById(R.id.buttonAjouterPhoto);
        buttonSoumettre = findViewById(R.id.buttonSoumettre);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        buttonSoumettre.setOnClickListener(v -> {
            int selectedId = radioGroupType.getCheckedRadioButtonId();
            RadioButton selectedRadio = findViewById(selectedId);
            String type = (selectedRadio != null) ? selectedRadio.getText().toString() : "Non spécifié";
            String description = editTextDescription.getText().toString();
            LatLng position = (currentMarker != null) ? currentMarker.getPosition() : null;

            if (position != null) {
                Signalement signalement = new Signalement(
                        0, type, description,
                        position.latitude, position.longitude,
                        ""
                );
                apiService.creerSignalement(signalement).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(CreerSignalementActivity.this, "Signalement envoyé avec succès", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CreerSignalementActivity.this, "Erreur d'envoi : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CreerSignalementActivity.this, "Erreur réseau : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(this, "Veuillez sélectionner une localisation", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        moveMarker(currentLocation);
                    }
                });

        mMap.setOnMapClickListener(this::moveMarker);
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
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapView.getMapAsync(this);
            } else {
                Toast.makeText(this, "Permission localisation refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Gérer le cycle de vie de la MapView
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) mapView.onLowMemory();
    }

}
/*




 */
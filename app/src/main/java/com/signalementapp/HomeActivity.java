package com.signalementapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private ImageButton fullscreenButton, alertButton, historyButton;
    private Button creerSignalementButton;
    private LinearLayout bottomButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialisation
        fragmentContainer = findViewById(R.id.fragment_container);
        fullscreenButton = findViewById(R.id.fullscreenButton);
        alertButton = findViewById(R.id.alertButton);
        historyButton = findViewById(R.id.historyButton);
        creerSignalementButton = findViewById(R.id.creerSignalementButton);
        bottomButtons = findViewById(R.id.bottom_buttons);

        // Affiche la carte dès le début
        replaceFragment(new MapFragment());

        // Bouton plein écran (optionnel, à implémenter dans MapFragment)
        fullscreenButton.setOnClickListener(v -> {
            // Ici, à faire : envoyer un message à MapFragment pour activer plein écran
        });

        // Icône Signalements
        alertButton.setOnClickListener(v -> replaceFragment(new TousLesSignalementsFragment()));

        // Icône Historique
        historyButton.setOnClickListener(v -> replaceFragment(new HistoriqueSignalementsFragment()));

        // Bouton Créer un signalement
        creerSignalementButton.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, CreerSignalementActivity.class));
        });
    }

    private void replaceFragment(@NonNull Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
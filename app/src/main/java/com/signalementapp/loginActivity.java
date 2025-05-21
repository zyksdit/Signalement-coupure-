package com.signalementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button loginButton;
    private TextView textViewForgotPassword, textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Récupération des vues
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        textViewForgotPassword = findViewById(R.id.forgot_password);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Bouton mot de passe oublié
        textViewForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(loginActivity.this, forgotPasswordActivity.class);
            startActivity(intent);
        });

        // Bouton inscription
        textViewSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Bouton connexion
        loginButton.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // Authentification simulée (à remplacer par un appel au back-end plus tard)
                if (username.equals("admin") && password.equals("admin")) {
                    // Connexion réussie
                    Intent intent = new Intent(loginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Fermer la page de connexion
                } else {
                    // Échec de la connexion
                    Toast.makeText(this, "Nom d'utilisateur ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

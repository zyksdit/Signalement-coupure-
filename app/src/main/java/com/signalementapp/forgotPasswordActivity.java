package com.signalementapp;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class forgotPasswordActivity extends AppCompatActivity {

    EditText emailInput;
    Button sendResetLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInput = findViewById(R.id.email_input);
        sendResetLink = findViewById(R.id.send_reset_link);

        sendResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(forgotPasswordActivity.this, "Veuillez entrer votre adresse e-mail", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(forgotPasswordActivity.this, "Format de l'adresse e-mail invalide", Toast.LENGTH_SHORT).show();
                } else {
                    // Tu pourras ajouter ici l'envoi réel du lien
                    Toast.makeText(forgotPasswordActivity.this, "Un lien de réinitialisation a été envoyé à " + email, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

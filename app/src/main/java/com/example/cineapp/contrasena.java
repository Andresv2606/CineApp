package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class contrasena extends AppCompatActivity {

    ImageButton btnFlecha;
    TextView tvVolverLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena);


        btnFlecha = findViewById(R.id.btnFlecha);
        tvVolverLogin = findViewById(R.id.tvVolverLogin);


        btnFlecha.setOnClickListener(v -> {
            Intent intent = new Intent(contrasena.this, Login.class);
            startActivity(intent);
            finish();
        });


        tvVolverLogin.setOnClickListener(v -> {
            Intent intent = new Intent(contrasena.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
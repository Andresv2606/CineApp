package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Horario extends AppCompatActivity {

    ImageButton btnAtras;
    Button btnVolverInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_horario);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(v -> {
            Intent intent = new Intent(Horario.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        btnVolverInicio = findViewById(R.id.btn_volverInicio);
        btnVolverInicio.setOnClickListener(v -> {
            Intent intent = new Intent(Horario.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
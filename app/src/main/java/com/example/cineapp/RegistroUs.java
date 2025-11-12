package com.example.cineapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.ImageButton;

public class RegistroUs extends AppCompatActivity {

    Button btn_registrar;
    ImageButton btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_us);


        btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUs.this, Login.class);
            startActivity(intent);
            finish();
        });


        btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUs.this, Login.class);
            startActivity(intent);
            finish();
        });
    }
}
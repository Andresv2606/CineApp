package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_salir;
    Button btnBuscarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botón de salir
        btn_salir = findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        });

        // Botón "Buscar película" → solo este funcionará por ahora
        btnBuscarPelicula = findViewById(R.id.btnBuscarPelicula);
        btnBuscarPelicula.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarPelicula.class);
            startActivity(intent);
        });

    }
}
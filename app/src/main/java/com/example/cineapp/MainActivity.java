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
    Button btnBuscarPelicula, btnBuscarCine, btnVerPrecios,btnHorarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_salir = findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
        });


        btnBuscarPelicula = findViewById(R.id.btnBuscarPelicula);
        btnBuscarPelicula.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarPelicula.class);
            startActivity(intent);
        });


        btnBuscarCine = findViewById(R.id.btnBuscarCine);
        btnBuscarCine.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarCine.class);
            startActivity(intent);
        });


        btnVerPrecios = findViewById(R.id.btnVerPrecios);
        btnVerPrecios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VerPrecios.class);
            startActivity(intent);
        });
        btnHorarios = findViewById(R.id.btnHorarios);
        btnHorarios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Horario.class);
            startActivity(intent);

        });
    }
}
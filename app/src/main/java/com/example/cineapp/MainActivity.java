package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

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


        btnBuscarPelicula = findViewById(R.id.btnPelicula);
        btnBuscarPelicula.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarPelicula.class);
            startActivity(intent);
        });


        btnBuscarCine = findViewById(R.id.btnCine);
        btnBuscarCine.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BuscarCine.class);
            startActivity(intent);
        });


        btnVerPrecios = findViewById(R.id.btnPrecios);
        btnVerPrecios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VerPrecios.class);
            startActivity(intent);
        });
        btnHorarios = findViewById(R.id.btnHorarios);
        btnHorarios.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Horario.class);
            startActivity(intent);

        });
        Button btnTop = findViewById(R.id.btnTop);

        btnTop.setOnClickListener(v -> {
            Intent i = new Intent(this, TopPeliculas.class);
            startActivity(i);
        });

    }
}
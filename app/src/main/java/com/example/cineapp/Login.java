package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class Login extends AppCompatActivity {

    EditText txt_persona, txt_contrasena;
    Button btnIniciarSesion, btnRegistrate, btncontraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txt_persona = findViewById(R.id.txt_persona);
        txt_contrasena = findViewById(R.id.txt_contrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        btnRegistrate = findViewById(R.id.btnRegistrate);
        btncontraseña = findViewById(R.id.btncontraseña);


        btnIniciarSesion.setOnClickListener(v -> {

            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        btnRegistrate.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, RegistroUs.class);
            startActivity(intent);
        });


        btncontraseña.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, contrasena.class);
            startActivity(intent);
        });
    }
}
package com.example.cineapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaResponse;
import com.example.cineapp.models.Persona;
import com.example.cineapp.models.RegistroResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroPeliculas extends AppCompatActivity {
    Button btn_registrar;
    ImageButton btnVolver;
    EditText txt_nombre, txt_genero, txt_clasificacion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_peliculas);

        txt_nombre = findViewById(R.id.txt_nombre);
        txt_genero = findViewById(R.id.txt_genero);
        txt_clasificacion = findViewById(R.id.txt_clasificacion);

        btn_registrar = findViewById(R.id.btn_registrar);
        btnVolver = findViewById(R.id.btnVolver);

        btn_registrar.setOnClickListener(v -> registrarPelicula());

        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroPeliculas.this, BuscarPelicula.class);
            startActivity(intent);
            finish();
        });
    }

    private void registrarPelicula() {
        Pelicula pelicula = new Pelicula(
                txt_nombre.getText().toString(),
                txt_genero.getText().toString(),
                txt_clasificacion.getText().toString(),
                1
        );

        RetrofitClient.getApiService().registrarPelicula(pelicula)
                .enqueue(new Callback<PeliculaResponse>() {
                    @Override
                    public void onResponse(Call<PeliculaResponse> call, Response<PeliculaResponse> response) {

                        try {
                            Log.e("API_ERROR", "Error: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e("API_ERROR", "Error sin cuerpo");
                        }

                        if (!response.isSuccessful()) {
                            Toast.makeText(RegistroPeliculas.this,
                                    "Error: " + response.code(), Toast.LENGTH_LONG).show();
                            return;
                        }

                        PeliculaResponse res = response.body();
                        Toast.makeText(RegistroPeliculas.this,
                                res.getMessage(), Toast.LENGTH_LONG).show();

                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Intent intent = new Intent(RegistroPeliculas.this, BuscarPelicula.class);
                            startActivity(intent);
                            finish(); // Evita que regresen al registro usando "atrás"
                        }, 1200);
                    }

                    @Override
                    public void onFailure(Call<PeliculaResponse> call, Throwable t) {
                        Toast.makeText(RegistroPeliculas.this,
                                "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();

                        System.out.println("Error de conexión: " + t.getMessage());
                    }
                });
    }
}
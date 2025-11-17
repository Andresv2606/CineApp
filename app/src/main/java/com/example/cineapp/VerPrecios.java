package com.example.cineapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.models.Precio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPrecios extends AppCompatActivity {

    RecyclerView rvPrecios;
    PrecioAdapter adapter;
    List<Precio> listaPrecios;

    Button btnAgregarPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_precios);

        rvPrecios = findViewById(R.id.rvPrecios);
        rvPrecios.setLayoutManager(new LinearLayoutManager(this));

        btnAgregarPrecio = findViewById(R.id.btnAgregarPrecio);
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int rolUsuario = prefs.getInt("id_rol", 2); // 2 = usuario normal

        if (rolUsuario == 1) {
            btnAgregarPrecio.setVisibility(View.VISIBLE);

            btnAgregarPrecio.setOnClickListener(v -> {
                Intent intent = new Intent(VerPrecios.this, RegistrarPrecio.class);
                startActivity(intent);
            });
        } else {
            btnAgregarPrecio.setVisibility(View.GONE);
        }

        cargarPrecios();

        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
    }

    private void cargarPrecios() {
        RetrofitClient.getApiService().getPrecios().enqueue(new Callback<List<Precio>>() {
            @Override
            public void onResponse(Call<List<Precio>> call, Response<List<Precio>> response) {

                if (response.body() == null) {
                    Log.e("API", "Respuesta vacía");
                    return;
                }

                listaPrecios = response.body();
                adapter = new PrecioAdapter(listaPrecios);
                rvPrecios.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Precio>> call, Throwable t) {
                Log.e("API", "Error precios: " + t.getMessage());
            }
        });
    }
}

package com.example.cineapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.models.Precio;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPrecios extends AppCompatActivity {

    private RecyclerView rvPrecios;
    private PrecioAdapter adapter;
    private Button btnAgregarPrecio;

    private int rolUsuario;
    private List<Precio> listaPrecios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_precios);

        rvPrecios = findViewById(R.id.rvPrecios);
        rvPrecios.setLayoutManager(new LinearLayoutManager(this));

        btnAgregarPrecio = findViewById(R.id.btnAgregarPrecio);
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        rolUsuario = prefs.getInt("id_rol", 2);
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

    @Override
    protected void onResume() {
        super.onResume();
        cargarPrecios();
    }

    private void cargarPrecios() {

        RetrofitClient.getApiService().getPrecios().enqueue(new Callback<List<Precio>>() {
            @Override
            public void onResponse(Call<List<Precio>> call, Response<List<Precio>> response) {
                if (!response.isSuccessful() || response.body() == null) {
                    Log.e("API", "Error al obtener precios");
                    return;
                }

                listaPrecios = response.body();

                adapter = new PrecioAdapter(listaPrecios, rolUsuario);
                rvPrecios.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Precio>> call, Throwable t) {
                Log.e("API", "Error precios: " + t.getMessage());
            }
        });
    }
}

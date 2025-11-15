package com.example.cineapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerPrecios extends AppCompatActivity {

    RecyclerView rvPrecios;
    PrecioAdapter adapter;
    List<Precio> listaPrecios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_precios);

        rvPrecios = findViewById(R.id.rvPrecios);
        rvPrecios.setLayoutManager(new LinearLayoutManager(this));

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

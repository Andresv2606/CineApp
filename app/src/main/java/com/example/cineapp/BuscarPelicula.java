package com.example.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarPelicula extends AppCompatActivity {

    RecyclerView rvPeliculas;
    PeliculaAdapter adapter;
    List<Pelicula> listaOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pelicula);

        rvPeliculas = findViewById(R.id.rvPeliculas);

        rvPeliculas.setLayoutManager(new LinearLayoutManager(this));
        rvPeliculas.setNestedScrollingEnabled(true);
        rvPeliculas.setHasFixedSize(false);
        cargarPeliculas();

        // --- BOTÓN BUSCAR ---
        Button btnBuscar = findViewById(R.id.btnBuscarPel);
        EditText txtBuscar = findViewById(R.id.txt_buscarPelicula);

        btnBuscar.setOnClickListener(v -> {
            String texto = txtBuscar.getText().toString().toLowerCase();

            List<Pelicula> filtrada = new ArrayList<>();
            for (Pelicula p : listaOriginal) {
                if (p.getTitulo().toLowerCase().contains(texto)) {
                    filtrada.add(p);
                }
            }

            adapter.actualizarLista(filtrada);
        });
    }

    private void cargarPeliculas() {
        RetrofitClient.getApiService().getPeliculas().enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {
                listaOriginal = response.body();
                adapter = new PeliculaAdapter(listaOriginal);
                rvPeliculas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }
}

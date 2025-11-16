package com.example.cineapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.adapters.CineAdapter;
import com.example.cineapp.Cine;
import com.example.cineapp.Precio;
import com.example.cineapp.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarCine extends AppCompatActivity {

    EditText txt_BuscarCine;
    Button btnBuscarCine, btnVolver;
    RecyclerView rvCines;

    List<Cine> listaCines = new ArrayList<>();
    List<Precio> preciosGlobales = new ArrayList<>();

    CineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cine);

        txt_BuscarCine = findViewById(R.id.txt_BuscarCine);
        btnBuscarCine = findViewById(R.id.btnBuscarCine);
        btnVolver = findViewById(R.id.btnVolver);
        rvCines = findViewById(R.id.rvCines);

        rvCines.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CineAdapter(listaCines);
        rvCines.setAdapter(adapter);

        cargarPreciosGlobales();
        cargarTodosLosCines();

        btnBuscarCine.setOnClickListener(v -> {
            String nombre = txt_BuscarCine.getText().toString().trim();
            if (nombre.isEmpty()) {
                cargarTodosLosCines();
            } else {
                buscarCine(nombre);
            }
        });

        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarPreciosGlobales() {
        RetrofitClient.getApiService().getPrecios().enqueue(new Callback<List<Precio>>() {
            @Override
            public void onResponse(Call<List<Precio>> call, Response<List<Precio>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    preciosGlobales = response.body();
                    actualizarPreciosEnLista();
                }
            }

            @Override
            public void onFailure(Call<List<Precio>> call, Throwable t) {
                Log.e("API", "Error precios globales", t);
            }
        });
    }

    private void cargarTodosLosCines() {
        RetrofitClient.getApiService().getCines().enqueue(new Callback<List<Cine>>() {
            @Override
            public void onResponse(Call<List<Cine>> call, Response<List<Cine>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCines = response.body();
                    actualizarPreciosEnLista();
                }
            }

            @Override
            public void onFailure(Call<List<Cine>> call, Throwable t) {
                Toast.makeText(BuscarCine.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void buscarCine(String nombre) {
        RetrofitClient.getApiService().buscarCine(nombre).enqueue(new Callback<List<Cine>>() {
            @Override
            public void onResponse(Call<List<Cine>> call, Response<List<Cine>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCines = response.body();
                    actualizarPreciosEnLista();
                } else {
                    Toast.makeText(BuscarCine.this, "No se encontraron cines", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Cine>> call, Throwable t) {
                Toast.makeText(BuscarCine.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Asigna los precios globales a todos los cines
    private void actualizarPreciosEnLista() {
        for (Cine c : listaCines) {
            c.setPrecios(preciosGlobales);
        }
        adapter.updateList(listaCines);
    }
}
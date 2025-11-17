package com.example.cineapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarPelicula extends AppCompatActivity {

    RecyclerView rvPeliculas;
    PeliculaAdapter adapter;
    List<Pelicula> listaOriginal;
    Button btnDetalle, btnBuscar, btnVolver, btnAgregarPelicula;

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
        //btnDetalle = findViewById(R.id.btnDetalle);
        btnBuscar = findViewById(R.id.btnBuscarPel);
        btnVolver = findViewById(R.id.btnVolverBusPel);

        btnAgregarPelicula = findViewById(R.id.btnAddPelicula);

        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        int id_rol = prefs.getInt("id_rol", 2);
        Log.d("PREFS", "Leyendo id_rol: " + id_rol);


        // Ocultar Boton dependiendo el rol
        if ( id_rol != 1 ){
            btnAgregarPelicula.setVisibility(View.GONE);
        }

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

        btnAgregarPelicula.setOnClickListener(v -> {
            Intent intent = new Intent(BuscarPelicula.this, RegistroPeliculas.class);
            startActivity(intent);
            finish();
        });

        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarPeliculas() {
        RetrofitClient.getApiService().getPeliculas().enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {
                listaOriginal = response.body();
                adapter = new PeliculaAdapter(BuscarPelicula.this, listaOriginal);

                adapter.setOnEliminarClickListener(idPelicula -> {
                    eliminarPelicula(idPelicula);
                });
                rvPeliculas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }

    private void eliminarPelicula(int id) {
        Call<PeliculaResponse> call = RetrofitClient.getApiService().eliminarPelicula(id);

        call.enqueue(new Callback<PeliculaResponse>() {
            @Override
            public void onResponse(Call<PeliculaResponse> call, Response<PeliculaResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(BuscarPelicula.this, "Error al eliminar ", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(BuscarPelicula.this, "Película eliminada correctamente", Toast.LENGTH_SHORT).show();

                cargarPeliculas();
            }

            @Override
            public void onFailure(Call<PeliculaResponse> call, Throwable t) {
                Toast.makeText(BuscarPelicula.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Toast.makeText(this, "Eliminando película ID: " + id, Toast.LENGTH_SHORT).show();
    }

}

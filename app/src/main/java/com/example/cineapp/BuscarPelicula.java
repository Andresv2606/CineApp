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

    Button btnBuscar, btnVolver, btnAgregarPelicula;
    int id_rol; // variable global bien usada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_pelicula);

        //Referencias UI
        rvPeliculas = findViewById(R.id.rvPeliculas);
        btnBuscar = findViewById(R.id.btnBuscarPel);
        btnVolver = findViewById(R.id.btnVolverBusPel);
        btnAgregarPelicula = findViewById(R.id.btnAddPelicula);

        //Configuración RecyclerView
        rvPeliculas.setLayoutManager(new LinearLayoutManager(this));
        rvPeliculas.setNestedScrollingEnabled(true);
        rvPeliculas.setHasFixedSize(false);

        //Obtener ROL guardado en preferencias
        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        id_rol = prefs.getInt("id_rol", 2);
        Log.d("PREFS", "Leyendo id_rol: " + id_rol);

        //Si NO es admin ocultar botón agregar
        if (id_rol != 1) {
            btnAgregarPelicula.setVisibility(View.GONE);
        }

        //Cargar películas
        cargarPeliculas();

        EditText txtBuscar = findViewById(R.id.txt_buscarPelicula);

        //BOTÓN BUSCAR
        btnBuscar.setOnClickListener(v -> {
            String texto = txtBuscar.getText().toString().toLowerCase().trim();
            List<Pelicula> filtrada = new ArrayList<>();

            for (Pelicula p : listaOriginal) {
                if (p.getTitulo().toLowerCase().contains(texto)) {
                    filtrada.add(p);
                }
            }

            adapter.actualizarLista(filtrada);
        });

        //BOTÓN AGREGAR
        btnAgregarPelicula.setOnClickListener(v -> {
            startActivity(new Intent(BuscarPelicula.this, RegistroPeliculas.class));
            finish();
        });

        //BOTÓN VOLVER
        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarPeliculas() {
        RetrofitClient.getApiService().getPeliculas().enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(BuscarPelicula.this, "Error al obtener películas", Toast.LENGTH_SHORT).show();
                    return;
                }

                listaOriginal = response.body();
                adapter = new PeliculaAdapter(BuscarPelicula.this, listaOriginal, id_rol);

                adapter.setOnEliminarClickListener(idPelicula -> eliminarPelicula(idPelicula));

                rvPeliculas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }

    private void eliminarPelicula(int id) {
        RetrofitClient.getApiService().eliminarPelicula(id).enqueue(new Callback<PeliculaResponse>() {
            @Override
            public void onResponse(Call<PeliculaResponse> call, Response<PeliculaResponse> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(BuscarPelicula.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(BuscarPelicula.this, "Película eliminada correctamente", Toast.LENGTH_SHORT).show();
                cargarPeliculas(); //Recargar lista
            }

            @Override
            public void onFailure(Call<PeliculaResponse> call, Throwable t) {
                Toast.makeText(BuscarPelicula.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}


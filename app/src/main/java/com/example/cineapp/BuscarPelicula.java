package com.example.cineapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.models.EstadoRequest;
import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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


        rvPeliculas = findViewById(R.id.rvPeliculas);
        btnBuscar = findViewById(R.id.btnBuscarPel);
        btnVolver = findViewById(R.id.btnVolverBusPel);
        btnAgregarPelicula = findViewById(R.id.btnAddPelicula);


        rvPeliculas.setLayoutManager(new LinearLayoutManager(this));
        rvPeliculas.setNestedScrollingEnabled(true);
        rvPeliculas.setHasFixedSize(false);


        SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
        id_rol = prefs.getInt("id_rol", 2);
        Log.d("PREFS", "Leyendo id_rol: " + id_rol);


        if (id_rol != 1) {
            btnAgregarPelicula.setVisibility(View.GONE);
        }


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


        btnAgregarPelicula.setOnClickListener(v -> {
            startActivity(new Intent(BuscarPelicula.this, RegistroPeliculas.class));
            finish();
        });


        if (id_rol != 1) {
            btnAgregarPelicula.setVisibility(View.GONE);
            btnVolver.setVisibility(View.GONE);
        } else {
            btnVolver.setOnClickListener(v -> finish());
        }


        ImageButton btnSalir = findViewById(R.id.btn_salir);

        if (id_rol != 1) {
            btnSalir.setVisibility(View.VISIBLE);
            btnSalir.setOnClickListener(v -> {

                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(BuscarPelicula.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        } else {
            btnSalir.setVisibility(View.GONE);
        }
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
                SharedPreferences prefs = getSharedPreferences("USER_PREFS", MODE_PRIVATE);
                id_rol = prefs.getInt("id_rol", 2);
                // --- FILTRAR SOLO ACTIVAS ---
                List<Pelicula> soloActivas = new ArrayList<>();
                for (Pelicula p : listaOriginal) {
                    //Si NO es admin ocultar botón agregar
                    if (id_rol != 1) {
                        if (p.getEstado() == 1) {
                            soloActivas.add(p);
                        }
                    }else{
                        soloActivas.add(p);
                    }

                }
                listaOriginal = soloActivas;
                // ----------------------------

                adapter = new PeliculaAdapter(BuscarPelicula.this, listaOriginal, id_rol);

                adapter.setOnEliminarClickListener((idPelicula, estadoActual) -> {
                    cambiarEstado(idPelicula, estadoActual);
                });

                rvPeliculas.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }

    private void cambiarEstado(int id, int estadoActual) {
        int estado = (estadoActual == 1) ? 0 : 1;
        EstadoRequest request = new EstadoRequest(estado);

        RetrofitClient.getApiService()
                .cambiarEstadoPelicula(id, request)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (!response.isSuccessful()) {
                            try {
                                String errorBody = response.errorBody().string();
                                Log.e("API_ERROR", errorBody);
                                Toast.makeText(BuscarPelicula.this,
                                        "Error al actualizar estado",
                                        Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // mensaje según estado
                        String msg = (estadoActual == 1)
                                ? "Película deshabilitada"
                                : "Película habilitada";

                        Toast.makeText(BuscarPelicula.this, msg, Toast.LENGTH_SHORT).show();

                        cargarPeliculas(); // recargar lista
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(BuscarPelicula.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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


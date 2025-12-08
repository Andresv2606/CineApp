package com.example.cineapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineapp.adapters.CineAdapter;
import com.example.cineapp.models.ActorItem;
import com.example.cineapp.models.Cine;
import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaCinesResponse;
import com.example.cineapp.models.PeliculaDetalleResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetallePeliculaActivity extends AppCompatActivity {
    TextView txtTitulo, txtGenero, txtClasificacion;
    RecyclerView rvCinesPelicula;
    int idPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);

        txtTitulo = findViewById(R.id.txtTituloDetalle);
        txtGenero = findViewById(R.id.txtGeneroDetalle);
        txtClasificacion = findViewById(R.id.txtClasificacionDetalle);
        rvCinesPelicula = findViewById(R.id.rvCinesPelicula);

        rvCinesPelicula.setLayoutManager(new LinearLayoutManager(this));

        idPelicula = getIntent().getIntExtra("id_pelicula", -1);

        cargarDetalles();
        cargarCines();
        ImageView btnVolver = findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(v -> {
            onBackPressed();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });


    }

    private void cargarDetalles() {

        RetrofitClient.getApiService()
                .getPeliculaDetalle(idPelicula)
                .enqueue(new Callback<PeliculaDetalleResponse>() {
                    @Override
                    public void onResponse(Call<PeliculaDetalleResponse> call,
                                           Response<PeliculaDetalleResponse> response) {

                        if (!response.isSuccessful() || response.body() == null) {
                            Log.e("API", "Respuesta inválida");
                            return;
                        }

                        Pelicula p = response.body().getPelicula();
                        if (p == null) return;

                        txtTitulo.setText(p.getTitulo());
                        txtGenero.setText("Género: " + p.getGenero());
                        txtClasificacion.setText("Clasificación: " + p.getClasificacion());

                        TextView txtDirector = findViewById(R.id.txtDirector);
                        txtDirector.setText("Director: " + p.getDirector());

                        TextView txtActores = findViewById(R.id.txtActores);
                        StringBuilder builder = new StringBuilder();

                        if (response.body().getActores() != null) {
                            for (ActorItem actor : response.body().getActores()) {
                                if (builder.length() > 0) builder.append(", ");
                                builder.append(actor.getNombre());
                            }
                        }

                        txtActores.setText("Actores: " + builder);
                    }

                    @Override
                    public void onFailure(Call<PeliculaDetalleResponse> call, Throwable t) {
                        Log.e("API", "ERROR DETALLE: " + t.getMessage());
                    }
                });
    }

    private void cargarCines() {

        RetrofitClient.getApiService()
                .getCinesPelicula(idPelicula)
                .enqueue(new Callback<PeliculaCinesResponse>() {
                    @Override
                    public void onResponse(Call<PeliculaCinesResponse> call,
                                           Response<PeliculaCinesResponse> response) {

                        if (response.body() == null) return;

                        List<Cine> lista = response.body().getCines();


                        CineAdapter adapter = new CineAdapter(
                                lista,
                                idPelicula,
                                txtTitulo.getText().toString()   // ← AQUÍ
                        );

                        rvCinesPelicula.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<PeliculaCinesResponse> call, Throwable t) {
                        Log.e("API", "ERROR CINES: " + t.getMessage());
                    }
                });
    }

}
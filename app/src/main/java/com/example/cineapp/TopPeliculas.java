package com.example.cineapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineapp.models.TopPelicula;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopPeliculas extends AppCompatActivity {

    LinearLayout contenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_peliculas);

        contenedor = findViewById(R.id.contenedorTop);

        cargarTop();
        ImageView btnAtras = findViewById(R.id.btnAtras);

        btnAtras.setOnClickListener(v -> {
            finish();
        });

    }

    private void cargarTop() {
        RetrofitClient.getApiService().getTopPeliculas().enqueue(new Callback<List<TopPelicula>>() {
            @Override
            public void onResponse(Call<List<TopPelicula>> call, Response<List<TopPelicula>> response) {
                if(response.isSuccessful() && response.body() != null){
                    mostrarLista(response.body());
                } else {
                    Toast.makeText(TopPeliculas.this,"Sin datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TopPelicula>> call, Throwable t) {
                Toast.makeText(TopPeliculas.this,"Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarLista(List<TopPelicula> lista){
        for (TopPelicula item : lista){

            TextView tv = new TextView(this);
            tv.setText( item.getPelicula() + " Reservas: " + item.getReservas());
            tv.setTextSize(18);
            tv.setPadding(20,20,20,20);

            contenedor.addView(tv);
        }
    }
}

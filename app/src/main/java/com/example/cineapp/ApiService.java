package com.example.cineapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("peliculas")
    Call<List<Pelicula>> getPeliculas();

    @GET("precios")
    Call<List<Precio>> getPrecios();
}

package com.example.cineapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("peliculas")
    Call<List<Pelicula>> getPeliculas();

    @GET("precios")
    Call<List<Precio>> getPrecios();

    @POST("auth")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("personas")
    Call<RegistroResponse> registrarPersona(@Body Persona persona);

    @GET("cines")
    Call<List<Cine>> getCines();

    @GET("cines/buscar")
    Call<List<Cine>> buscarCine(@Query("nombre") String nombre);
}

package com.example.cineapp;

import com.example.cineapp.models.Cine;
import com.example.cineapp.models.LoginRequest;
import com.example.cineapp.models.LoginResponse;
import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.Persona;
import com.example.cineapp.models.Precio;
import com.example.cineapp.models.RegistroResponse;
import com.example.cineapp.models.PrecioResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("peliculas")
    Call<List<Pelicula>> getPeliculas();

    @GET("precios")
    Call<List<Precio>> getPrecios();

    @POST("precios")
    Call<PrecioResponse> registrarPrecio(@Body Precio precio);

    @GET("precios")
    Call<List<Precio>> getPrecios(@Query("id_cine") String idCine);

    @POST("auth")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("personas")
    Call<RegistroResponse> registrarPersona(@Body Persona persona);

    @GET("cines")
    Call<List<Cine>> getCines();

    @GET("cines/buscar")
    Call<List<Cine>> buscarCine(@Query("nombre") String nombre);
}

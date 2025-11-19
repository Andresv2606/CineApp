package com.example.cineapp;

import com.example.cineapp.models.Cine;
import com.example.cineapp.models.LoginRequest;
import com.example.cineapp.models.LoginResponse;
import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaResponse;
import com.example.cineapp.models.Persona;
import com.example.cineapp.models.Precio;
import com.example.cineapp.models.RegistroResponse;
import com.example.cineapp.models.PrecioResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("peliculas")
    Call<List<Pelicula>> getPeliculas();

    @POST("peliculas")
    Call<PeliculaResponse> registrarPelicula(@Body Pelicula pelicula);

    @PUT("peliculas")
    Call<PeliculaResponse> actualizarPelicula(@Body Pelicula pelicula);

    @DELETE("peliculas/{id}")
    Call<PeliculaResponse> eliminarPelicula(@Path("id") int idPelicula);

    @GET("precios")
    Call<List<Precio>> getPrecios();

    @POST("precios")
    Call<PrecioResponse> registrarPrecio(@Body Precio precio);

    @PUT("precios/{id}")
    Call<PrecioResponse> actualizarPrecio(@Path("id") String id, @Body Precio precio);

    @GET("precios")
    Call<List<Precio>> getPrecios(@Query("id_cine") String idCine);
    @DELETE("precios/{id}")
    Call<PrecioResponse> eliminarPrecio(@Path("id") String id);


    @POST("auth")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("personas")
    Call<RegistroResponse> registrarPersona(@Body Persona persona);

    @GET("cines")
    Call<List<Cine>> getCines();

    @GET("cines/buscar")
    Call<List<Cine>> buscarCine(@Query("nombre") String nombre);
}

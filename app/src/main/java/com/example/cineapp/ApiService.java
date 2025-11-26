package com.example.cineapp;

import com.example.cineapp.models.Cine;
import com.example.cineapp.models.HorarioResponse;
import com.example.cineapp.models.LoginRequest;
import com.example.cineapp.models.LoginResponse;
import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaCinesResponse;
import com.example.cineapp.models.PeliculaDetalleResponse;
import com.example.cineapp.models.PeliculaResponse;
import com.example.cineapp.models.Persona;
import com.example.cineapp.models.Precio;
import com.example.cineapp.models.RegistroResponse;
import com.example.cineapp.models.PrecioResponse;
import com.example.cineapp.models.ReservaRequest;
import com.example.cineapp.models.ReservaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("peliculas")
    Call<List<Pelicula>> getPeliculas();

    @GET("peliculas/{id}/cines")
    Call<PeliculaCinesResponse> getCinesPelicula(@Path("id") int id);

    @GET("horarios")
    Call<HorarioResponse> getHorarios(
            @Query("pelicula") int idPelicula,
            @Query("cine") int idCine
    );
    @POST("reservas")
    Call<ReservaResponse> crearReserva(@Body ReservaRequest request);
    @POST("peliculas")
    Call<PeliculaResponse> registrarPelicula(@Body Pelicula pelicula);
    @PUT("peliculas")
    Call<PeliculaResponse> actualizarPelicula(@Body Pelicula pelicula);
    @GET("peliculas/{id}")
    Call<PeliculaDetalleResponse> getPeliculaDetalle(@Path("id") int id);
    @DELETE("peliculas/{id}")
    Call<PeliculaResponse> eliminarPelicula(@Path("id") int idPelicula);
    @GET("precios")
    Call<List<Precio>> getPrecios();
    @POST("precios")
    Call<PrecioResponse> registrarPrecio(@Body Precio precio);
    @PUT("precios/{id}")
    Call<PrecioResponse> actualizarPrecio(@Path("id") String id, @Body Precio precio);
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

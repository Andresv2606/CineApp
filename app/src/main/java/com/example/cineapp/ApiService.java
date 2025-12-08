package com.example.cineapp;

import com.example.cineapp.models.CambiarPassRequest;
import com.example.cineapp.models.CambiarPassResponse;
import com.example.cineapp.models.Cine;
import com.example.cineapp.models.EstadoRequest;
import com.example.cineapp.models.HorarioResponse;
import com.example.cineapp.models.LoginRequest;
import com.example.cineapp.models.LoginResponse;
import com.example.cineapp.models.Pelicula;
import com.example.cineapp.models.PeliculaCinesResponse;
import com.example.cineapp.models.PeliculaDetalleResponse;
import com.example.cineapp.models.PeliculaResponse;
import com.example.cineapp.models.Persona;
import com.example.cineapp.models.Precio;
import com.example.cineapp.models.PrecioResponse;
import com.example.cineapp.models.RegistroResponse;
import com.example.cineapp.models.ReservaRequest;
import com.example.cineapp.models.ReservaResponse;
import com.example.cineapp.models.TopPelicula;

import java.util.List;

import okhttp3.ResponseBody;
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

    @GET("peliculas/{id}")
    Call<PeliculaDetalleResponse> getPeliculaDetalle(@Path("id") int id);

    @GET("peliculas/{id}/cines")
    Call<PeliculaCinesResponse> getCinesPelicula(@Path("id") int id);


    @POST("peliculas/{id}/estado")
    Call<ResponseBody> cambiarEstadoPelicula(
            @Path("id") int id,
            @Body EstadoRequest estadoRequest
    );

    @POST("peliculas")
    Call<PeliculaResponse> registrarPelicula(@Body Pelicula pelicula);

    @PUT("peliculas")
    Call<PeliculaResponse> actualizarPelicula(@Body Pelicula pelicula);

    @DELETE("peliculas/{id}")
    Call<PeliculaResponse> eliminarPelicula(@Path("id") int idPelicula);

    // === HORARIOS ===
    @GET("horarios")
    Call<HorarioResponse> getHorarios(
            @Query("pelicula") int idPelicula,
            @Query("cine") int idCine
    );

    // === RESERVAS ===
    @POST("reservas")
    Call<ReservaResponse> crearReserva(@Body ReservaRequest request);

    @GET("reservas/precio")
    Call<PrecioResponse> getPrecioHorario(@Query("id_horario") int idHorario);

    // === PRECIOS ===
    @GET("precios")
    Call<List<Precio>> getPrecios();
    @POST("precios")
    Call<PrecioResponse> registrarPrecio(@Body Precio precio);
    @PUT("precios/{id}")
    Call<PrecioResponse> actualizarPrecio(@Path("id") String id, @Body Precio precio);

    @DELETE("precios/{id}")
    Call<PrecioResponse> eliminarPrecio(@Path("id") String id);

    // === AUTENTICACIÓN ===
    @POST("auth")
    Call<LoginResponse> login(@Body LoginRequest request);

    // Recuperar Contraseña
    @POST("password_reset/solicitar")
    Call<CambiarPassResponse> solicitar(@Body CambiarPassRequest request);
    @POST("password_reset/verificar")
    Call<CambiarPassResponse> verificar(@Body CambiarPassRequest request);
    @POST("password_reset/cambiar")
    Call<CambiarPassResponse> cambiar(@Body CambiarPassRequest request);

    @POST("personas")
    Call<RegistroResponse> registrarPersona(@Body Persona persona);
    @GET("cines")
    Call<List<Cine>> getCines();
    @GET("cines/buscar")
    Call<List<Cine>> buscarCine(@Query("nombre") String nombre);
    @GET("reservas/top")
    Call<List<TopPelicula>> getTopPeliculas();


}
package com.example.cineapp.models;

import java.util.List;

public class PeliculaDetalleResponse {
    private Pelicula pelicula;
    private List<ActorItem> actores;

    public Pelicula getPelicula() {
        return pelicula;
    }

    public List<ActorItem> getActores() {
        return actores;
    }
}

package com.example.cineapp.models;

import java.util.List;

public class PeliculaCinesResponse {

    private Pelicula pelicula;
    private List<Cine> cines;

    public Pelicula getPelicula() {
        return pelicula;
    }

    public List<Cine> getCines() {
        return cines;
    }
}

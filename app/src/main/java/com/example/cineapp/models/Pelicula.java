package com.example.cineapp.models;

public class Pelicula {

    private int id_pelicula;
    private String titulo;
    private String genero;
    private String clasificacion;
    private int id_director;
    private String director;

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getId_pelicula() { return id_pelicula; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public String getClasificacion() { return clasificacion; }

    public Pelicula(String nombre, String genero, String clasificacion, Integer director) {
        this.titulo = nombre;
        this.genero = genero;
        this.clasificacion = clasificacion;
        this.id_director = director;
    }

    public void setId_pelicula(int id_pelicula) { this.id_pelicula = id_pelicula; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public void setId_director(int id_director) { this.id_director = id_director; }
}

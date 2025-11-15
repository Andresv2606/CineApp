package com.example.cineapp;

public class Pelicula {

    private String id_pelicula;
    private String titulo;
    private String genero;
    private String clasificacion;
    private String director;


    public Pelicula() {}

    public String getId_pelicula() { return id_pelicula; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public String getClasificacion() { return clasificacion; }
    public String getDirector() { return director; }


    public void setId_pelicula(String id_pelicula) { this.id_pelicula = id_pelicula; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public void setDirector(String director) { this.director = director; }
}

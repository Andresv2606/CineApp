package com.example.cineapp.models;

public class Precio {

    private String id_precio;
    private String descripcion;
    private int id_cine;
    private double valor;
    public Precio() {
    }
    public Precio(String descripcion, double valor, int id_cine) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.id_cine = id_cine;
    }
    public Precio(String id_precio, String descripcion, double valor, int id_cine) {
        this.id_precio = id_precio;
        this.descripcion = descripcion;
        this.valor = valor;
        this.id_cine = id_cine;
    }
    public String getId_precio() { return id_precio; }
    public String getDescripcion() { return descripcion; }
    public int getId_cine() { return id_cine; }
    public double getValor() { return valor; }

    // SETTERS (necesarios para Retrofit/Gson)
    public void setId_precio(String id_precio) { this.id_precio = id_precio; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setId_cine(int id_cine) { this.id_cine = id_cine; }
    public void setValor(double valor) { this.valor = valor; }
}

package com.example.cineapp.models;
import com.google.gson.annotations.SerializedName;

public class Precio {
    @SerializedName("nombre_cine")
    private String nombre_Cine;
    private String id_precio;
    private String descripcion;
    private int id_cine;
    private double valor;
    public Precio() {
    }
    public Precio(String nombreCine, String descripcion, double valor, int id_cine) {
        this.nombre_Cine = nombreCine;
        this.descripcion = descripcion;
        this.valor = valor;
        this.id_cine = id_cine;
    }
    public Precio(String nombreCine, String id_precio, String descripcion, double valor, int id_cine) {
        this.nombre_Cine = nombreCine;
        this.id_precio = id_precio;
        this.descripcion = descripcion;
        this.valor = valor;
        this.id_cine = id_cine;
    }

    public Precio(String descripcion, double valor, int idCineSeleccionado) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.id_cine = idCineSeleccionado;
    }

    public String getId_precio() { return id_precio; }
    public String getDescripcion() { return descripcion; }
    public int getId_cine() { return id_cine; }
    public double getValor() { return valor; }
    public String getNombreCine(){ return nombre_Cine; }

    // SETTERS (necesarios para Retrofit/Gson)
    public void setId_precio(String id_precio) { this.id_precio = id_precio; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setId_cine(int id_cine) { this.id_cine = id_cine; }
    public void setValor(double valor) { this.valor = valor; }
    public void setNombreCine(String nombreCine) {this.nombre_Cine = nombreCine; }
}

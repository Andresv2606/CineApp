package com.example.cineapp.models;

public class Precio {

    private String id_precio;
    private String descripcion;
    private String id_cine;
    private String valor;

    // Constructor SOLO para registrar precio
    public Precio(String descripcion, String valor) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.id_cine = "1"; // 👈 siempre cine 1
    }

    public String getId_precio() {
        return id_precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId_cine() {
        return id_cine;
    }

    public String getValor() {
        return valor;
    }
    public void setId_cine(String id_cine) {
        this.id_cine = id_cine;
    }

}

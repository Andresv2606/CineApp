package com.example.cineapp;

import java.util.List;

public class Cine {
    private int id_cine;
    private String nombre;
    private String direccion;
    private String telefono;

    // Lista de precios cargada desde la app
    private List<Precio> precios;

    public int getId_cine() { return id_cine; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }

    // precios
    public List<Precio> getPrecios() { return precios; }
    public void setPrecios(List<Precio> precios) { this.precios = precios; }
}

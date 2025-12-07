package com.example.cineapp.models;

import com.google.gson.annotations.SerializedName;

public class Cine {
    @SerializedName("id_cine")
    private int id_cine;
    @SerializedName("nombre")
    private String nombre;
    private String direccion;
    private String telefono;

    public int getId_cine() {
        return id_cine;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }
}

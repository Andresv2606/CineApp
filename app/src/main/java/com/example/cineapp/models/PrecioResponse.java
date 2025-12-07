package com.example.cineapp.models;

import com.google.gson.annotations.SerializedName;

public class PrecioResponse {
    @SerializedName("precio_unitario")
    private double precioUnitario;
    @SerializedName("es_fin_de_semana")
    private boolean esFinDeSemana;
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public boolean isEsFinDeSemana() {
        return esFinDeSemana;
    }
}
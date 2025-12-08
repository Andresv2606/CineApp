package com.example.cineapp.models;

public class EstadoRequest {
    private int estado;

    public EstadoRequest(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
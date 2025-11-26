package com.example.cineapp.models;

public class ReservaRequest {
    private int id_persona;
    private int id_horario;
    private int cantidad_boletos;

    public ReservaRequest(int idPersona, int idHorario, int cantidad) {
        this.id_persona = idPersona;
        this.id_horario = idHorario;
        this.cantidad_boletos = cantidad;
    }
}



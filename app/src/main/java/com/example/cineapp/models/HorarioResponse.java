package com.example.cineapp.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HorarioResponse {

    @SerializedName("horarios")
    private List<Horario> horarios;

    public List<Horario> getHorarios() {
        return horarios;
    }

    // Modelo interno para cada horario
    public static class Horario {

        @SerializedName("id_horario")
        private int id_horario;

        @SerializedName("hora")
        private String hora;

        @SerializedName("nombre_sala")
        private String nombre_sala;

        public int getId_horario() {
            return id_horario;
        }

        public String getHora() {
            return hora;
        }

        public String getNombre_sala() {
            return nombre_sala;
        }
    }
}

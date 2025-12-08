package com.example.cineapp.models;

public class CambiarPassRequest {
    private String correo;
    private String codigo;
    private String nueva_contrasena;

    // Constructor para solicitar código
    public CambiarPassRequest(String correo) {
        this.correo = correo;
    }

    // Constructor para verificar código
    public CambiarPassRequest(String correo, String codigo) {
        this.correo = correo;
        this.codigo = codigo;
    }

    // Constructor para cambiar contraseña
    public CambiarPassRequest(String correo, String codigo, String nueva_contrasena) {
        this.correo = correo;
        this.codigo = codigo;
        this.nueva_contrasena = nueva_contrasena;
    }

    public String getCorreo() { return correo; }
    public String getCodigo() { return codigo; }
    public String getNueva_contrasena() { return nueva_contrasena; }
}

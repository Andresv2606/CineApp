package com.example.cineapp.models;

public class Usuario {
    private int id_login;
    private int id_persona;
    private int id_rol;
    private String usuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String nombre_rol;

    public int getId_login() { return id_login; }
    public String getUsuario() { return usuario; }
    public int getId_persona() { return id_persona; }
    public int getId_rol() { return id_rol; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public String getNombreRol() { return nombre_rol; }
}

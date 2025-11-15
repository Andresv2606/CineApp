package com.example.cineapp;

public class Persona {
    private String nombre;
    private String apellido;
    private String email;
    private Integer id_rol;
    private String usuario;
    private String contrasena;
    private String documento;
    private String telefono;

    public Persona(String nombre, String apellido, String email, Integer id_rol, String usuario, String contrasena, String documento, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.id_rol = id_rol;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.documento = documento;
        this.telefono = telefono;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getEmail() { return email; }
    public Integer getId_rol() { return id_rol; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String  getDocumento() { return documento; }
    public String getTelefono() { return telefono; }

}

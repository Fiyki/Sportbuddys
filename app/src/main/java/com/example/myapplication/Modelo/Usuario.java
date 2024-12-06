package com.example.myapplication.Modelo;

public class Usuario {
    private String nombre;
    private int edad;
    private String descripcion;

    // Constructor vacío para Firebase
    public Usuario() {}

    public Usuario(String nombre, int edad, String descripcion) {
        this.nombre = nombre;
        this.edad = edad;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}


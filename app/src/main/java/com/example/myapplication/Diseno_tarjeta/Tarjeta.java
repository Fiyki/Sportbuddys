package com.example.myapplication.Diseno_tarjeta;

public class Tarjeta {
    private String nombre;
    private int edad;
    private String descripcion;
    private int imagenResId;

    public Tarjeta(String nombre, int edad, String descripcion, int imagenResId) {
        this.nombre = nombre;
        this.edad = edad;
        this.descripcion = descripcion;
        this.imagenResId = imagenResId;
    }

    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public String getDescripcion() { return descripcion; }
    public int getImagenResId() { return imagenResId; }
}

package com.ejemplo.tmdb;

public class elenco_dto {

    private int id;
    private String nombre;
    private String personaje;
    private String foto;
    private int orden;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPersonaje() { return personaje; }
    public void setPersonaje(String personaje) { this.personaje = personaje; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
}

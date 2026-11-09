package com.ejemplo.tmdb;

import java.util.ArrayList;
import java.util.List;

public class pelicula_dto {

    private int id;
    private String titulo;
    private String sinopsis;
    private String fechaEstreno;
    private int duracion;
    private List<String> generos;
    private String clasificacion;
    private String cartel;
    private double calificacion;
    private String estado;

    public static final String IMG_BASE = "https://image.tmdb.org/t/p/w500";

    public static pelicula_dto from(pelicula p) {
        return from(p, "NR");
    }

    public static pelicula_dto from(pelicula p, String clasificacion) {
        pelicula_dto dto = new pelicula_dto();
        dto.id = p.getId();
        dto.titulo = p.getTitle();
        dto.sinopsis = p.getOverview();
        dto.fechaEstreno = p.getReleaseDate();
        dto.duracion = p.getRuntime();
        dto.calificacion = p.getVoteAverage();
        dto.estado = p.getStatus();
        dto.clasificacion = clasificacion != null ? clasificacion : "NR";
        dto.cartel = p.getPosterPath() != null ? IMG_BASE + p.getPosterPath() : null;

        List<String> gs = new ArrayList<>();
        if (p.getGenres() != null) {
            for (pelicula.Genre g : p.getGenres()) gs.add(g.getName());
        }
        dto.generos = gs;
        return dto;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getSinopsis() { return sinopsis; }
    public String getFechaEstreno() { return fechaEstreno; }
    public int getDuracion() { return duracion; }
    public List<String> getGeneros() { return generos; }
    public String getClasificacion() { return clasificacion; }
    public String getCartel() { return cartel; }
    public double getCalificacion() { return calificacion; }
    public String getEstado() { return estado; }
}
    

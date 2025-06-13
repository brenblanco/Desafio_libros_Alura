package com.brendablanco.LibrosAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gutendex_id")
    private Long gutendexId;         // ✅ Este debe ser Long (no int)

    private String titulo;
    private String autor;
    private String lenguaje;
    private String tema;

    @Column(name = "cant_descarga")
    private Double cant_descarga;    // ✅ Si viene con decimales, mejor usar Double

    @Column(name = "anio_cumpleanios")
    private Integer anio_cumpleanios;

    @Column(name = "anio_muerte")
    private Integer anio_muerte;

    // ===== Getters y Setters =====


    public Long getId() {
        return id;


    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGutendexId() {
        return gutendexId;
    }

    public void setGutendexId(Long gutendexId) {
        this.gutendexId = gutendexId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Double getCant_descarga() {
        return cant_descarga;
    }

    public void setCant_descarga(Double cant_descarga) {
        this.cant_descarga = cant_descarga;
    }

    public Integer getAnio_cumpleanios() {
        return anio_cumpleanios;
    }

    public void setAnio_cumpleanios(Integer anio_cumpleanios) {
        this.anio_cumpleanios = anio_cumpleanios;
    }

    public Integer getAnio_muerte() {
        return anio_muerte;
    }

    public void setAnio_muerte(Integer anio_muerte) {
        this.anio_muerte = anio_muerte;
    }
}

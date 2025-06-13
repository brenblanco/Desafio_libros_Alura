package com.brendablanco.LibrosAlura.service;

import com.brendablanco.LibrosAlura.model.Libro;

import java.util.List;

public interface LibroService {
    List<Libro> buscarPorTitulo(String titulo);
    List<Libro> librosPorIdioma(String idioma);
    List<Libro> listarLibros();
    List<String> listarAutores();
    List<Libro> buscarAutoresVivosEnAnio(int anio);
    List<Libro> filtrarLibrosUnicos(List<Libro> libros);
}

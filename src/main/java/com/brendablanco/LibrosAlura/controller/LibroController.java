package com.brendablanco.LibrosAlura.controller;

import com.brendablanco.LibrosAlura.model.Libro;
import com.brendablanco.LibrosAlura.service.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Libro>> buscarPorTitulo(@RequestParam String titulo) {
        List<Libro> libros = libroService.buscarPorTitulo(titulo);
        if (libros.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(libros);
    }
}

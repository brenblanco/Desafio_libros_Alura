package com.brendablanco.LibrosAlura.repository;

import com.brendablanco.LibrosAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    List<Libro> findByLenguajeIgnoreCase(String lenguaje);

    boolean existsByGutendexId(Long gutendexId);

}

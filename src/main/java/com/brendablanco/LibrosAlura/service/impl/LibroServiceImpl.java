package com.brendablanco.LibrosAlura.service.impl;

import com.brendablanco.LibrosAlura.dto.LibroResponse;
import com.brendablanco.LibrosAlura.dto.ResultItem;
import com.brendablanco.LibrosAlura.model.Libro;
import com.brendablanco.LibrosAlura.repository.LibroRepository;
import com.brendablanco.LibrosAlura.service.LibroService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final RestTemplate restTemplate;
    private final String apiUrl = "https://gutendex.com/books";

    public LibroServiceImpl(LibroRepository libroRepository, RestTemplate restTemplate) {
        this.libroRepository = libroRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        // 1. Primero buscar localmente
        List<Libro> existentes = libroRepository.findByTituloContainingIgnoreCase(titulo);
        if (!existentes.isEmpty()) {
            return filtrarLibrosUnicos(existentes);
        }

        // 2. Si no hay, buscar en la API
        String url = apiUrl + "?search=" + UriUtils.encode(titulo, StandardCharsets.UTF_8);
        System.out.println("📡 Llamando a URL: " + url);
        LibroResponse response = restTemplate.getForObject(url, LibroResponse.class);

        // 3. Mostrar respuesta
        System.out.println("JSON API: " + new Gson().toJson(response));
        List<Libro> nuevos = new ArrayList<>();

        if (response != null && response.getResults() != null) {
            Set<String> claves = new HashSet<>();
            for (ResultItem item : response.getResults()) {
                String autor = item.getAuthors().isEmpty() ? "Desconocido" : item.getAuthors().get(0).getName();
                String tituloLibro = item.getTitle();
                String clave = autor.trim().toLowerCase() + "::" + tituloLibro.trim().toLowerCase();
                if (!claves.add(clave)) continue;
                Libro libro = mapResultItemToLibro(item);
                if (!libroRepository.existsByGutendexId(libro.getGutendexId())) {
                    libroRepository.save(libro);
                    nuevos.add(libro);
                }
            }
        }

        if (nuevos.isEmpty()) {
            System.out.println("⚠️ No se encontraron libros nuevos.");
        }
        return filtrarLibrosUnicos(nuevos);
    }

    @Override
    public List<Libro> librosPorIdioma(String idioma) {
        // 1. Llamada API por idioma
        String url = apiUrl + "?languages=" + UriUtils.encode(idioma, StandardCharsets.UTF_8);
        System.out.println("📡 Llamando a URL de idioma: " + url);
        LibroResponse response = restTemplate.getForObject(url, LibroResponse.class);
        if (response == null || response.getResults() == null) {
            return Collections.emptyList();
        }
        // 2. Mapear
        List<Libro> lista = response.getResults().stream()
                .map(this::mapResultItemToLibro)
                .collect(Collectors.toList());
        // 3. Guardar nuevos
        for (Libro lib : lista) {
            if (!libroRepository.existsByGutendexId(lib.getGutendexId())) {
                libroRepository.save(lib);
            }
        }
        // 4. Devolver únicos
        List<Libro> todos = libroRepository.findByLenguajeIgnoreCase(idioma);
        return filtrarLibrosUnicos(todos);
    }

    @Override
    public List<Libro> listarLibros() {
        return filtrarLibrosUnicos(libroRepository.findAll());
    }

    @Override
    public List<String> listarAutores() {
        return libroRepository.findAll().stream()
                .map(Libro::getAutor)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> buscarAutoresVivosEnAnio(int anio) {
        List<Libro> vivos = new ArrayList<>();
        for (Libro lib : libroRepository.findAll()) {
            Integer n = lib.getAnio_cumpleanios();
            Integer m = lib.getAnio_muerte();
            if (n != null && n <= anio && (m == null || m >= anio)) {
                vivos.add(lib);
            }
        }
        return filtrarLibrosUnicos(vivos);
    }

    @Override
    public List<Libro> filtrarLibrosUnicos(List<Libro> libros) {
        Set<String> autores = new HashSet<>();
        return libros.stream()
                .filter(l -> autores.add(l.getAutor()))
                .collect(Collectors.toList());
    }

    // Auxiliar para mapear DTO → Entidad
    private Libro mapResultItemToLibro(ResultItem item) {
        Libro libro = new Libro();
        libro.setGutendexId((long) item.getId());
        libro.setTitulo(item.getTitle());
        String autor = item.getAuthors().isEmpty() ? "Desconocido" : item.getAuthors().get(0).getName();
        libro.setAutor(autor);
        libro.setLenguaje(item.getLanguages().isEmpty()? "N/A": item.getLanguages().get(0));
        libro.setTema(item.getSubjects().isEmpty()? "General": item.getSubjects().get(0));
        libro.setCant_descarga(item.getDownload_count());
        if (!item.getAuthors().isEmpty()) {
            var a = item.getAuthors().get(0);
            libro.setAnio_cumpleanios(a.getBirth_year());
            libro.setAnio_muerte(a.getDeath_year());
        }
        return libro;
    }
}
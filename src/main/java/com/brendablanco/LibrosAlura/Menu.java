package com.brendablanco.LibrosAlura;

import com.brendablanco.LibrosAlura.model.Libro;
import com.brendablanco.LibrosAlura.service.IdiomaService;
import com.brendablanco.LibrosAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Menu implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    @Autowired
    private IdiomaService idiomaService;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void run(String... args) {
        int opcion;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                opcion = -1;
            }
            switch (opcion) {
                case 1 -> buscarPorTitulo();
                case 2 -> listarTodosLosLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivosEnAnio();
                case 5 -> listarPorIdioma();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n=== Menú de Libros ===");
        System.out.println("1. Buscar libro por título");
        System.out.println("2. Listar todos los libros");
        System.out.println("3. Listar autores");
        System.out.println("4. Buscar autores vivos en un año");
        System.out.println("5. Listar libros por idioma");
        System.out.println("0. Salir");
        System.out.print("Seleccione opción: ");
    }

    private void buscarPorTitulo() {
        System.out.print("Ingrese el título del libro: ");
        String t = scanner.nextLine();
        List<Libro> lista = libroService.buscarPorTitulo(t);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron libros.");
        } else {
            for (Libro lib : lista) {
                System.out.println("-----------------------LIBRO----------------------");
                System.out.println("TITULO: " + lib.getTitulo());
                System.out.println("AUTOR: " + lib.getAutor());
                System.out.println("IDIOMA: " + lib.getLenguaje());
                System.out.println("NUMERO DE DESCARGAS: " + lib.getCant_descarga());
                System.out.println("------------------------------------------------------");
            }
        }
    }

    private void listarTodosLosLibros() {
        List<Libro> lista = libroService.listarLibros();
        if (lista.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            for (Libro lib : lista) {
                System.out.println("-----------------------LIBRO----------------------");
                System.out.println("TITULO: " + lib.getTitulo());
                System.out.println("AUTOR: " + lib.getAutor());
                System.out.println("IDIOMA: " + lib.getLenguaje());
                System.out.println("NUMERO DE DESCARGAS: " + lib.getCant_descarga());
                System.out.println("------------------------------------------------------");
            }
        }
    }

    private void listarAutores() {
        List<Libro> libros = libroService.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            Map<String, List<Libro>> porAutor = libros.stream()
                    .collect(Collectors.groupingBy(Libro::getAutor));

            for (Map.Entry<String, List<Libro>> entry : porAutor.entrySet()) {
                String autor = entry.getKey();
                List<Libro> librosAutor = entry.getValue();
                Libro ejemplo = librosAutor.get(0);
                System.out.println("AUTOR: " + autor);
                System.out.println("FECHA DE NACIMIENTO: " +
                        (ejemplo.getAnio_cumpleanios() != null ? ejemplo.getAnio_cumpleanios() : "Desconocido"));
                System.out.println("FECHA DE FALLECIMIENTO: " +
                        (ejemplo.getAnio_muerte() != null ? ejemplo.getAnio_muerte() : "Desconocido"));
                // LIBROS entre comillas dentro de corchetes
                String librosFormateados = librosAutor.stream()
                        .map(Libro::getTitulo)
                        .map(t -> "\"" + t + "\"")
                        .collect(Collectors.joining(", ", "[", "]"));
                System.out.println("LIBROS: " + librosFormateados);
                System.out.println();
            }
        }
    }

    private void autoresVivosEnAnio() {
        System.out.print("Ingrese el año: ");
        int anio;
        try {
            anio = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número válido.");
            return;
        }
        List<Libro> libros = libroService.buscarAutoresVivosEnAnio(anio);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anio);
            return;
        }
        Map<String, List<Libro>> porAutor = libros.stream()
                .collect(Collectors.groupingBy(Libro::getAutor));
        for (Map.Entry<String, List<Libro>> entry : porAutor.entrySet()) {
            String autor = entry.getKey();
            List<Libro> librosAutor = entry.getValue();
            Libro ejemplo = librosAutor.get(0);
            System.out.println("AUTOR: " + autor);
            System.out.println("FECHA DE NACIMIENTO: " +
                    (ejemplo.getAnio_cumpleanios() != null ? ejemplo.getAnio_cumpleanios() : "Desconocido"));
            System.out.println("FECHA DE FALLECIMIENTO: " +
                    (ejemplo.getAnio_muerte() != null ? ejemplo.getAnio_muerte() : "Desconocido"));
            String librosFormateados = librosAutor.stream()
                    .map(Libro::getTitulo)
                    .map(t -> "\"" + t + "\"")
                    .collect(Collectors.joining(", ", "[", "]"));
            System.out.println("LIBROS: " + librosFormateados);
            System.out.println();
        }
    }


    private void listarPorIdioma() {
        Map<String, String> idiomasDisponibles = idiomaService.getIdiomas();

        System.out.println("Ingrese el idioma que desea buscar:");
        idiomasDisponibles.forEach((codigo, nombre) ->
                System.out.println(" - " + codigo + " - " + nombre)
        );

        String lang;
        while (true) {
            System.out.print("Código de idioma: ");
            lang = scanner.nextLine().trim().toLowerCase();
            if (idiomaService.idiomaValido(lang)) break;
            System.out.println("Código inválido. Intente nuevamente.");
        }

        List<Libro> lista = libroService.librosPorIdioma(lang);

        if (lista.isEmpty()) {
            System.out.println("No se encontraron libros en " + idiomaService.getNombreIdioma(lang));
            return;
        }

        System.out.println("\n Libros en " + idiomaService.getNombreIdioma(lang) + ":");
        for (Libro lib : lista) {
            System.out.println("------------------------LIBRO---------------------");
            System.out.println("TITULO: " + lib.getTitulo());
            System.out.println("AUTOR: " + lib.getAutor());
            System.out.println("IDIOMA: " + lib.getLenguaje());
            System.out.println("NUMERO DE DESCARGAS: " + lib.getCant_descarga());
            System.out.println("----------------------------------------------------");
        }

    }
}
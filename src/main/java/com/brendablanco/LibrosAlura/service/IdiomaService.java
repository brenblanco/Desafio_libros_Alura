package com.brendablanco.LibrosAlura.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
public class IdiomaService {
    private final Map<String, String> idiomas;

    public IdiomaService() {
        Map<String, String> tempIdiomas = null;
        try {
            InputStream input = getClass().getResourceAsStream("/idiomas.json");
            if (input != null) {
                try (var reader = new InputStreamReader(input)) {
                    Type mapType = new TypeToken<Map<String, String>>() {}.getType();
                    tempIdiomas = new Gson().fromJson(reader, mapType);
                }
            }
        } catch (Exception e) {
            System.err.println(" Error al leer idiomas.json: " + e.getMessage());
        }

        // Si no se pudo cargar, se usa este mapa por defecto
        if (tempIdiomas == null || tempIdiomas.isEmpty()) {
            tempIdiomas = new HashMap<>();
            tempIdiomas.put("es", "Español");
            tempIdiomas.put("en", "Inglés");
            tempIdiomas.put("fr", "Francés");
            tempIdiomas.put("pt", "Portugués");
            System.out.println(" Cargando idiomas por defecto.");
        }

        this.idiomas = tempIdiomas;
    }

    public Map<String, String> getIdiomas() {
        return idiomas;
    }

    public boolean idiomaValido(String codigo) {
        return idiomas.containsKey(codigo);
    }

    public String getNombreIdioma(String codigo) {
        return idiomas.getOrDefault(codigo, "Desconocido");
    }
}

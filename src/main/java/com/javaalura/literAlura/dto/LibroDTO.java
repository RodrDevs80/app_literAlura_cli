package com.javaalura.literAlura.dto;

import com.javaalura.literAlura.model.Autor;

import java.util.List;

public record LibroDTO(
        String titulo,
        Autor autor,
        List<String> idiomas,
        Double numeroDeDescargas) {
    @Override
    public String toString() {
        return """
           -------LIBRO-------
           Título: %s
           Autor: %s
           Idiomas: %s
           Número de descargas: %s
           -------------------
           """.formatted(
                titulo,
                autor != null ? autor.getNombre() : "Desconocido",
                idiomas,
                numeroDeDescargas
        );
    }
}

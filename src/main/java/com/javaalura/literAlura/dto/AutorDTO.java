package com.javaalura.literAlura.dto;

import java.util.List;

public record AutorDTO(
        String nombre,
        Integer anioNacimiento,
        Integer anioMuerte,
        List<String> libros) {
    @Override
    public String toString() {
        return """
           -------Autores-------
           Nombre: %s
           Año de nacimiento: %s
           Año de muerte: %s
           Libros: %s
           -------------------
           """.formatted(
                nombre,
                anioNacimiento,
                anioMuerte,
                libros

        );
    }
}

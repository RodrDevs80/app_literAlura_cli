package com.javaalura.literAlura.services;

import com.javaalura.literAlura.dto.AutorDTO;
import com.javaalura.literAlura.dto.LibroDTO;
import com.javaalura.literAlura.model.Autor;
import com.javaalura.literAlura.model.Libro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MostrarLibrosAndAutores {
    @Autowired
    private ServiceAutor serviceAutor;

    public void mostarLibroEnDB(Libro libro, Autor autor){
        System.out.println("*******************LIBRO+++************************");
        System.out.println("Titulo: "+ libro.getTitulo());
        System.out.println("Autor: "+ autor.getNombre());
        System.out.println("Idiomas: "+ libro.getIdiomas());
        System.out.println("Numero de descargas: "+ libro.getNumeroDeDescargas());
        System.out.println("****************************************************");
    }
    public void mostrarListaDeLibros(List<LibroDTO> libros){
        libros.stream().forEach(System.out::println);
    }
    public void mostrarListaDeAutores(List<AutorDTO> autores){
        autores.stream().forEach(System.out::println);
    }
}

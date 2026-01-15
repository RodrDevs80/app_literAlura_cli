package com.javaalura.literAlura.services;

import com.javaalura.literAlura.model.Autor;

import com.javaalura.literAlura.model.Libro;
import com.javaalura.literAlura.repository.RepositoryAutor;
import com.javaalura.literAlura.repository.RepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Validaciones {
    @Autowired
    private RepositoryAutor repositoryAutor;
    @Autowired
    private RepositoryLibro repositoryLibro;

    public Autor verificarSiExisteAutorEnLaBaseDeDatos(String libroNombre) {
        Autor autorByNombre = repositoryAutor.findByNombre(libroNombre);
        if (autorByNombre == null) {
            return null;
        } else {
            return autorByNombre;
        }
    }

    public Optional<Libro> verificarSiElLibroEstaEnLaBaseDedatos(String libroBuscado) {
        Optional<Libro> libroByTitulo = repositoryLibro
                .findByTituloContainingIgnoreCase(libroBuscado);
        return libroByTitulo;
    }
}

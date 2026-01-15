package com.javaalura.literAlura.services;


import com.javaalura.literAlura.dto.AutorDTO;
import com.javaalura.literAlura.dto.LibroDTO;
import com.javaalura.literAlura.model.Autor;
import com.javaalura.literAlura.model.Libro;
import com.javaalura.literAlura.repository.RepositoryAutor;
import com.javaalura.literAlura.repository.RepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceAutor {
    @Autowired
    private RepositoryAutor repositoryAutor;
    @Autowired
    private RepositoryLibro repositoryLibro;

    public List<AutorDTO> listaDeAutores(){
        List<Autor> autorAll = repositoryAutor.findAllWithLibros();
        return autorAll.stream()
                .map(a->new AutorDTO(a.getNombre(),a.getAnioNacimiento()
                        ,a.getAnioMuerte(),a.getLibros().stream()
                        .map(Libro::getTitulo).toList()))
                .collect(Collectors.toList());
    }

    public List<AutorDTO> getAutoresPorVivosPorAnio(Integer anioInt) {
        List<Autor> muerteLessThan = repositoryAutor.findAutoresVivosAntesDeAnio(anioInt);
        return muerteLessThan.stream()
                .map(a->new AutorDTO(a.getNombre(),a.getAnioNacimiento()
                        ,a.getAnioMuerte(),a.getLibros().stream()
                        .map(Libro::getTitulo).toList()))
                .collect(Collectors.toList());
    }
}

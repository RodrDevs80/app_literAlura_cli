package com.javaalura.literAlura.services;

import com.javaalura.literAlura.dto.LibroDTO;
import com.javaalura.literAlura.model.Autor;
import com.javaalura.literAlura.model.DatosLibros;
import com.javaalura.literAlura.model.Libro;
import com.javaalura.literAlura.repository.RepositoryAutor;
import com.javaalura.literAlura.repository.RepositoryLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceLibro {
    @Autowired
    private RepositoryLibro repositoryLibro;
    @Autowired
    private RepositoryAutor repositoryAutor;
    @Autowired
    private MostrarLibrosAndAutores mostrarLibrosAndAutores;

    public void crearLibroEnBaseDeDatos(DatosLibros libros) {
        String nombre=libros.autor().get(0).nombre();
        Integer anioNacimiento= libros.autor().get(0).fechaDeNacimiento();
        Integer anioMuerte= libros.autor().get(0).fechaDeMuerte();
        Autor nuevoAutor = new Autor(nombre,anioNacimiento,anioMuerte );
        System.out.println("Guardando.....");
        Autor autorNew = repositoryAutor.save(nuevoAutor);
        Libro nuevoLibro= new Libro(libros.titulo(),autorNew,libros.idiomas(),libros.numeroDeDescargas());
        Libro libroNew = repositoryLibro.save(nuevoLibro);
        mostrarLibrosAndAutores.mostarLibroEnDB(libroNew,autorNew);
    }
    public List<LibroDTO> listaDeLibros(){
        List<Libro> listaDeLibros = repositoryLibro.findAll();
        return  listaDeLibros.stream()
                .map(l->new LibroDTO(l.getTitulo(),
                        l.getAutor(),
                        l.getIdiomas(),
                        l.getNumeroDeDescargas()))
                .collect(Collectors.toList());
    }

    public List<LibroDTO> getListaDeLibrosPorIdioma(String idioma) {
        List<Libro> libros = repositoryLibro.findAll();
        return  libros.stream()
                .filter(l->l.getIdiomas().get(0).contains(idioma))
                        .map(l->new LibroDTO(l.getTitulo(),
                        l.getAutor(),
                        l.getIdiomas(),
                        l.getNumeroDeDescargas()))
                .collect(Collectors.toList());
    }


    public void crearLibroEnDBConAutorEncontrado(DatosLibros libros, Autor autor) {
        Libro nuevoLibro= new Libro(libros.titulo(),autor,libros.idiomas(),libros.numeroDeDescargas());
        Libro libroNew = repositoryLibro.save(nuevoLibro);
        mostrarLibrosAndAutores.mostarLibroEnDB(libroNew,autor);
    }
}

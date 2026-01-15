package com.javaalura.literAlura.repository;

import com.javaalura.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryAutor extends JpaRepository<Autor,Long> {

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros")
    List<Autor> findAllWithLibros();

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros WHERE a.nombre = :nombre")
    Autor findByNombre(String nombre);

    @Query("SELECT DISTINCT a FROM Autor a LEFT JOIN FETCH a.libros WHERE "
            + "(a.anioMuerte IS NULL OR a.anioMuerte > :anio) "
            + "AND a.anioNacimiento <= :anio")
    List<Autor> findAutoresVivosAntesDeAnio(Integer anio);
}

package com.javaalura.literAlura.repository;

import com.javaalura.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RepositoryLibro extends JpaRepository<Libro,Long> {
   Optional<Libro> findByTituloContainingIgnoreCase(String titulo);
}

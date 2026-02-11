package com.aluracursos.literalura.repository;
import com.aluracursos.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libros,Long> {


    Optional<Libros> findByTituloContainsIgnoreCase(String nombreLibro);

    List<Libros> findByIdiomas(String idioma);
}

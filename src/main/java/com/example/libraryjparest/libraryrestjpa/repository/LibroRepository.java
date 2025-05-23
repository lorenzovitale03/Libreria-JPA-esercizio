package com.example.libraryjparest.libraryrestjpa.repository;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}

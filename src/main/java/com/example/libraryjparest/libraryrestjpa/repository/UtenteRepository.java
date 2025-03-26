package com.example.libraryjparest.libraryrestjpa.repository;

import com.example.libraryjparest.libraryrestjpa.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
}

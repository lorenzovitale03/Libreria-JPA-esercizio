package com.example.libraryjparest.libraryrestjpa.repository;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.models.Prestito;
import com.example.libraryjparest.libraryrestjpa.models.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrestitoRepository extends JpaRepository<Prestito, Long> {
    //true = ha prestiti attivi
    //false = ha prestiti non attivi
    boolean existsByUtenteAndRestituitoFalse(Utente utente);
    Prestito findByUtenteAndLibroAndRestituitoFalse(Utente utente, Libro libro);
    boolean existsByLibroAndRestituitoFalse(Libro libro);
}

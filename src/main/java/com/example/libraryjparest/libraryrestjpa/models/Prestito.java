package com.example.libraryjparest.libraryrestjpa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "prestiti")
@JsonIgnoreProperties({"utente"})  // Ignora i campi utente e libro durante la serializzazione

public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private Boolean restituito;

    //La relazione vuol dire : piu' prestiti a un singolo utente
    @ManyToOne
    @JoinColumn(name = "utente_id",nullable = false)
    @JsonBackReference
    private Utente utente;

    //Relazione di piu' libri a un singolo utente
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

}

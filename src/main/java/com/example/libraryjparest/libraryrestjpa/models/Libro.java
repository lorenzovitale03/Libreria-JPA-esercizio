package com.example.libraryjparest.libraryrestjpa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "libri")
@JsonIgnoreProperties({"prestiti"})  // Ignora i prestiti durante la serializzazione

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titolo;
    @Column(nullable = false)
    private String autore;
    private Integer annoPubblicazione;
    private String genere;
    private String tipo;
    private Boolean disponibile;

    //Singolo libro , piu' prestiti
    @OneToMany(mappedBy = "libro")
    @JsonManagedReference
    private List<Prestito> prestiti;

}

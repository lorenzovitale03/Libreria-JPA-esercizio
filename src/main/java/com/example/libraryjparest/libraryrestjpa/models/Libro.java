package com.example.libraryjparest.libraryrestjpa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "libri")

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

}

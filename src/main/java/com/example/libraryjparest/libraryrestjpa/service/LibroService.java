package com.example.libraryjparest.libraryrestjpa.service;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired LibroRepository libroRepository;

    public List<Libro> recuperaLibri(){
        return libroRepository.findAll();
    }


    public Libro creaLibro(Libro libro){
        return libroRepository.save(libro);
    }


    public Optional<Libro> recuperaLibro(Long id){
        return libroRepository.findById(id);
    }


    public void eliminaLibro(Long id){
        libroRepository.deleteById(id);
    }

    public Libro modificaLibro(Long id, Libro libro) {
        return libroRepository.findById(id)
                .map(libroEsistente -> {
                    if (libro.getTitolo() != null) libroEsistente.setTitolo(libro.getTitolo());
                    if (libro.getAutore() != null) libroEsistente.setAutore(libro.getAutore());
                    if (libro.getAnnoPubblicazione() != null) libroEsistente.setAnnoPubblicazione(libro.getAnnoPubblicazione());
                    if (libro.getDisponibile() != null) libroEsistente.setDisponibile(libro.getDisponibile());
                    if(libro.getTipo() != null) libroEsistente.setTipo(libro.getTipo());

                     libroRepository.save(libroEsistente);
                     libroRepository.flush(); //forza il salvataggio del libro nel db

                     return libroEsistente;
                })
                .orElse(null);
    }


}

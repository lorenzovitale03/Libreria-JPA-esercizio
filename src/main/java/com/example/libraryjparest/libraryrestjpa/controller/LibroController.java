package com.example.libraryjparest.libraryrestjpa.controller;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.service.LibroService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libri")
public class LibroController {

    @Autowired LibroService libroService;

    //Ottieni lista libri
    @GetMapping
    public List<Libro> getBooks(){
        return libroService.recuperaLibri();
    }

    //Creo un libro
    @PostMapping
    public Libro createBook(@RequestBody Libro libro){
        return libroService.creaLibro(libro);
    }

    //Leggo il libro da id
    //gestisce l'errore, se l'id non esiste, mi ritorna l'errore gestito dall'handler
    @GetMapping("{id}")
    public ResponseEntity<Libro> getBookById(@PathVariable Long id){
        Libro libroTrovaId = libroService.recuperaLibro(id)
                .orElseThrow(() -> new EntityNotFoundException("libro non trovato"));
        return ResponseEntity.ok(libroTrovaId);
    }

    //Elimino il libro da id
    //Gestisco l'errore, non posso cancellare un libro non esistente
    @DeleteMapping("{id}")
    public ResponseEntity<Libro> deleteBookById(@PathVariable Long id){
        Optional<Libro> libro = libroService.recuperaLibro(id);
        if(libro.isPresent()){
            libroService.eliminaLibro(id);
            return ResponseEntity.noContent().build();
        }else{
            throw new EntityExistsException("id non valido");
        }
    }

    //se il libro esiste, lo aggiorno nel db altrimenti mi restituisci nel corpo "id non valido"
    //quindi non posso aggiornare un libro non esistente.
    @PutMapping("{id}/update")
    public ResponseEntity<Libro> updateBookById(@PathVariable Long id, @RequestBody Libro libro){
        Optional<Libro> libro1 = libroService.recuperaLibro(id);
        if(libro1.isPresent()){
            Libro libroAggiornato = libroService.modificaLibro(id,libro);
            return ResponseEntity.ok(libroAggiornato);
        }else {
           throw new EntityExistsException("id non valido");
        }
    }

}

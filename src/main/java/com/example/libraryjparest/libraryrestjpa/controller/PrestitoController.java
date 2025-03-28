package com.example.libraryjparest.libraryrestjpa.controller;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.models.Prestito;
import com.example.libraryjparest.libraryrestjpa.models.Utente;
import com.example.libraryjparest.libraryrestjpa.repository.LibroRepository;
import com.example.libraryjparest.libraryrestjpa.repository.UtenteRepository;
import com.example.libraryjparest.libraryrestjpa.service.PrestitoService;
import com.example.libraryjparest.libraryrestjpa.service.UtenteService;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prestiti")
public class PrestitoController {

    @Autowired PrestitoService prestitoService;
    @Autowired UtenteRepository utenteRepository;
    @Autowired LibroRepository libroRepository;

    @PostMapping
    public ResponseEntity<Prestito> createLoan(@RequestParam Long idUser, @RequestParam Long idBook){
        Utente utente = utenteRepository.findById(idUser).orElse(null);
        Libro libro = libroRepository.findById(idBook).orElse(null);

        //Se l'utente e il libro non sono presenti nel db, gestisci l'eccezione.
        if(utente == null || libro == null){
            throw new EntityNotFoundException("libro e/o utente non trovati");
        }
        return ResponseEntity.ok(prestitoService.creaPrestito(utente,libro));
    }

    /*@GetMapping("/utenti/{id}/prestiti")
    public boolean activeLoans(@PathVariable Long id){
        Utente utente = utenteRepository.findById(id).orElse(null);

        //se l'utente non esiste nel database , non ci sono prestiti attivi
        if(utente == null){
            return false;
        }

        return prestitoService.prestitiAttivi(utente);
    }*/

    @PutMapping("/prestiti/{id}/{libroId}/restituisci")
    public ResponseEntity<Prestito> giveBackLoan(@PathVariable Long id, @PathVariable Long libroId, @RequestParam(required = false) Long libroPrendereId){
        //Per prima cosa mi recupero l'utente da id e il libro da id
        Utente utenteRestituisce = utenteRepository.findById(id).orElse(null);
        Libro libroRestituito = libroRepository.findById(libroId).orElse(null);

        //vado a verificare la presenza o meno di libri e utenti nel db
        if(utenteRestituisce == null || libroRestituito == null){
            throw new  EntityNotFoundException("libro e/o utente non trovati");
        }

        Libro libroPrendere = null;
        if(libroPrendereId != null){
            libroPrendere = libroRepository.findById(libroPrendereId). orElseThrow(() ->
            new EntityNotFoundException("prestito non attivo"));
        }

        return ResponseEntity.ok(prestitoService.restituisciPrestito(utenteRestituisce, libroRestituito, libroPrendere));

    }
}

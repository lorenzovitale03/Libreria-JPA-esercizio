package com.example.libraryjparest.libraryrestjpa.controller;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.models.Prestito;
import com.example.libraryjparest.libraryrestjpa.models.Utente;
import com.example.libraryjparest.libraryrestjpa.repository.LibroRepository;
import com.example.libraryjparest.libraryrestjpa.repository.UtenteRepository;
import com.example.libraryjparest.libraryrestjpa.service.PrestitoService;
import com.example.libraryjparest.libraryrestjpa.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/prestiti")
public class PrestitoController {

    @Autowired PrestitoService prestitoService;
    @Autowired UtenteRepository utenteRepository;
    @Autowired LibroRepository libroRepository;

    @PostMapping
    public Prestito createLoan(@RequestParam Long idUser, @RequestParam Long idBook){
        Utente utente = utenteRepository.findById(idUser).orElse(null);
        Libro libro = libroRepository.findById(idBook).orElse(null);

        //Se l'utente e il libro non sono presenti nel db, non ritorni nulla..
        //vado a gestire poi in seguito le eccezioni
        if(utente == null || libro == null){
            return null;
        }
        return prestitoService.creaPrestito(utente,libro);
    }

    @GetMapping("/utenti/{id}/prestiti")
    public boolean activeLoans(@PathVariable Long id){
        Utente utente = utenteRepository.findById(id).orElse(null);

        //se l'utente non esiste nel database , non ci sono prestiti attivi
        if(utente == null){
            return false;
        }

        return prestitoService.prestitiAttivi(utente);
    }

    @PutMapping("/prestiti/{id}/{libroId}/restituisci")
    public Prestito giveBackLoan(@PathVariable Long id, @PathVariable Long libroId){
        //Per prima cosa mi recupero l'utente da id e il libro da id
        Utente utenteRestituisce = utenteRepository.findById(id).orElse(null);
        Libro libroRestituito = libroRepository.findById(libroId).orElse(null);

        //vado a verificare la presenza o meno di libri e utenti nel db
        if(utenteRestituisce == null || libroRestituito == null){
            return null; //Non ci sono prestiti attivi
        }else {
            System.out.println("Libro restituito con successo!");
        }

        return prestitoService.restituisciPrestito(utenteRestituisce);

    }
}

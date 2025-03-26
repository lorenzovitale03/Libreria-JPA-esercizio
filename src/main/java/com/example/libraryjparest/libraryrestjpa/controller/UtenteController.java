package com.example.libraryjparest.libraryrestjpa.controller;

import com.example.libraryjparest.libraryrestjpa.models.Utente;
import com.example.libraryjparest.libraryrestjpa.service.UtenteService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/utenti")
public class UtenteController {

    @Autowired UtenteService utenteService;

    //CRUD NEL DB TRAMITE POSTMAN
    @GetMapping
    public List<Utente> leggiUtenti(){
        return utenteService.getUsers();
    }

    @PostMapping
    public Utente creaUtente(@RequestBody Utente utente){
        return utenteService.createUser(utente);
    }

    //Non posso leggere un utente che non esiste nel db,gestisco l'eccezione
    @GetMapping("{id}")
    public ResponseEntity<Utente> leggiUtenteId(@PathVariable Long id){
        Utente utenteTrovaId = utenteService.getUserById(id).
                orElseThrow(() -> new EntityNotFoundException("utente non trovato"));
        return ResponseEntity.ok(utenteTrovaId);
    }

}

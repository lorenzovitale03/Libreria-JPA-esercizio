package com.example.libraryjparest.libraryrestjpa.service;

import com.example.libraryjparest.libraryrestjpa.models.Utente;
import com.example.libraryjparest.libraryrestjpa.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired UtenteRepository utenteRepository;

    //CRUD
    public List<Utente> getUsers(){
        return utenteRepository.findAll();
    }


    public Utente createUser(Utente utente){
        if(utente.getDataRegistrazione() == null){
            utente.setDataRegistrazione(LocalDate.now());
        }
        return utenteRepository.save(utente);
    }


    public Optional<Utente> getUserById(Long id){
        return utenteRepository.findById(id);
    }

}

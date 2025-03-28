package com.example.libraryjparest.libraryrestjpa.service;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.models.Prestito;
import com.example.libraryjparest.libraryrestjpa.models.Utente;
import com.example.libraryjparest.libraryrestjpa.repository.LibroRepository;
import com.example.libraryjparest.libraryrestjpa.repository.PrestitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestitoService {

    @Autowired PrestitoRepository prestitoRepository;
    @Autowired LibroRepository libroRepository;

    //Post = creo il prestito per l'utente se non ha ancora nessun libro
    public Prestito creaPrestito(Utente utente, Libro libro){
        if(!prestitiAttivi(utente)){
            //Se non ha prestiti attivi, creo il prestito per lui
            Prestito prestitoUtente = new Prestito();
            prestitoUtente.setUtente(utente);
            prestitoUtente.setLibro(libro);
            prestitoUtente.setDataInizio(LocalDate.now());
            prestitoUtente.setRestituito(false);

            prestitoRepository.save(prestitoUtente);
        }
        return null;
    }

    //Get = leggo la lista di prestiti attivi
    public boolean prestitiAttivi(Utente utente){
        return prestitoRepository.existsByUtenteAndRestituitoFalse(utente);
    }

    //Put = modifica del prestito
    public Prestito restituisciPrestito(Utente utente, Libro libroRestituire, Libro libroPrendere){
        //trovo il prestito attivo dell'utente
        Prestito prestitoAttivo = prestitoRepository.findByUtenteAndLibroAndRestituitoFalse(utente,libroRestituire);
        //Se il libro di quel libro non è attivo, ritorna null
        if(prestitoAttivo == null){
            return null;
        }

        //Segna il prestito come restituito
        prestitoAttivo.setRestituito(true);
        prestitoAttivo.setDataFine(LocalDate.now());
        prestitoRepository.save(prestitoAttivo);

        //Il libro quindi è disponibile
        libroRestituire.setDisponibile(true);
        libroRepository.save(libroRestituire);

        // Se l'utente NON ha scelto un nuovo libro, fermati qui e ritorna il prestito chiuso
        if (libroPrendere == null) {
            return prestitoAttivo;
        }


        //se il libro non è disponibile ritorno null
       if(!libroPrendere.getDisponibile()){
           return null;
       }
        //se è disponibile creo un nuovo libro da prendere in prestito
       Prestito nuovoPrestito = new Prestito();
       nuovoPrestito.setUtente(utente);
       nuovoPrestito.setLibro(libroPrendere);
       nuovoPrestito.setDataInizio(LocalDate.now());
       nuovoPrestito.setRestituito(false);

       //di conseguenza setto il libro non disponibile e lo aggiorno nel db
        libroPrendere.setDisponibile(false);
        libroRepository.save(libroPrendere);

        //salvo il prestito
        return prestitoRepository.save(nuovoPrestito);
    }
}



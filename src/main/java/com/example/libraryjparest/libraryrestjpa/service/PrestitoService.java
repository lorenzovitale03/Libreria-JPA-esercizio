package com.example.libraryjparest.libraryrestjpa.service;

import com.example.libraryjparest.libraryrestjpa.models.Libro;
import com.example.libraryjparest.libraryrestjpa.models.Prestito;
import com.example.libraryjparest.libraryrestjpa.models.Utente;
import com.example.libraryjparest.libraryrestjpa.repository.PrestitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestitoService {

    @Autowired PrestitoRepository prestitoRepository;

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
    public Prestito restituisciPrestito(Utente utente){
        //trovo il prestito attivo dell'utente
        Prestito prestitoAttivo = prestitoRepository.findByUtenteAndRestituitoFalse(utente);
        //verinofico se il libro è ancora in prestito o no.
        //se è ancora in prestito, lo restituisco e setto la data in cui è stato consegnato

        if(prestitoAttivo != null){
            prestitoAttivo.setRestituito(true);
            prestitoAttivo.setDataFine(LocalDate.now());
        }

        //salvo le modifiche nel db
        return prestitoRepository.save(prestitoAttivo);
    }
}

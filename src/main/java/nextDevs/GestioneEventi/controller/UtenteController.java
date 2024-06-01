package nextDevs.GestioneEventi.controller;



import nextDevs.GestioneEventi.dto.UtenteDto;
import nextDevs.GestioneEventi.entity.Evento;
import nextDevs.GestioneEventi.entity.Utente;
import nextDevs.GestioneEventi.excepion.NotFoundException;
import nextDevs.GestioneEventi.service.UtenteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping("/api/utenti")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','ORGANIZZATORE')")
    public List<Utente> getAllUtentes(){
        return utenteService.getAllUtentes();
    }


    @GetMapping("/api/utenti/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','ORGANIZZATORE')")
    public Utente getUtenteById(@PathVariable int id){
        Optional<Utente> utenteOptional = utenteService.getUtenteById(id);

        if(utenteOptional.isPresent()){
            return utenteOptional.get();
        }
        else{
            throw new NotFoundException("Utente with id=" + id + " not found");
        }
    }


    @PutMapping("/api/utenti/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public Utente updateUtente(@PathVariable int id, @RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error->error.getDefaultMessage()).
                    reduce("", (s, s2) -> s+s2));
        }
        return utenteService.updateUtente(id, utenteDto);
    }

    @DeleteMapping("/api/utenti/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String deleteUtente(@PathVariable int id){
        return utenteService.deleteUtente(id);
    }

    @GetMapping("/api/utenti/{id}/eventi-prenotati")
    @PreAuthorize("hasAuthority('USER')")
    public List<Evento> getEventiPrenotati(@PathVariable int id) {
        return utenteService.getEventiPrenotati(id);
    }

    @DeleteMapping("/api/utenti/{utenteId}/eventi/{eventoId}/annulla-prenotazione")
    @PreAuthorize("hasAuthority('USER')")
    public String annullaPrenotazione(@PathVariable int utenteId, @PathVariable int eventoId) {
        return utenteService.annullaPrenotazione(utenteId, eventoId);
    }
}

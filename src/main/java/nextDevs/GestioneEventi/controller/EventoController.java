package nextDevs.GestioneEventi.controller;

import nextDevs.GestioneEventi.dto.EventoDto;
import nextDevs.GestioneEventi.entity.Evento;
import nextDevs.GestioneEventi.excepion.BadRequestException;
import nextDevs.GestioneEventi.excepion.NotFoundException;
import nextDevs.GestioneEventi.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/api/eventi")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String createEvento(@RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return eventoService.saveEvento(eventoDto);
    }

    @GetMapping("/api/eventi")
    public List<Evento> getAllEventi() {
        return eventoService.getAllEventi();
    }

    @GetMapping("/api/eventi/{id}")
    public Evento getEventoById(@PathVariable int id) {
        Optional<Evento> eventoOptional = eventoService.getEventoById(id);

        if (eventoOptional.isPresent()) {
            return eventoOptional.get();
        } else {
            throw new NotFoundException("Evento with id=" + id + " not found");
        }
    }

    @PutMapping("/api/eventi/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String updateEvento(@PathVariable int id, @RequestBody @Validated EventoDto eventoDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return eventoService.updateEvento(id, eventoDto);
    }

    @DeleteMapping("/api/eventi/{id}")
    @PreAuthorize("hasAuthority('ORGANIZZATORE')")
    public String deleteEvento(@PathVariable int id) {
        return eventoService.deleteEvento(id);
    }

    @PostMapping("/api/eventi/{id}/prenotazioni")
    @PreAuthorize("hasAuthority('USER')")
    public String prenotaPosto(@PathVariable int id) {
        return eventoService.prenotaPosto(id);
    }
}

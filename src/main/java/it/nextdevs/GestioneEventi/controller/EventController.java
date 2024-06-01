package it.nextdevs.GestioneEventi.controller;

import it.nextdevs.GestioneEventi.dto.EventDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.excepion.BadRequestException;
import it.nextdevs.GestioneEventi.excepion.NotFoundException;
import it.nextdevs.GestioneEventi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/api/events")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String createEvent(@RequestBody @Validated EventDTO eventDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return eventService.saveEvent(eventDto);
    }

    @GetMapping("/api/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/api/events/{id}")
    public Event getEventById(@PathVariable int id) {
        Optional<Event> eventOptional = eventService.getEventById(id);

        if (eventOptional.isPresent()) {
            return eventOptional.get();
        } else {
            throw new NotFoundException("Event with id:" + id + " not found");
        }
    }

    @PutMapping("/api/events/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String updateEvent(@PathVariable int id, @RequestBody @Validated EventDTO eventDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return eventService.updateEvent(id, eventDto);
    }

    @DeleteMapping("/api/events/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String deleteEvent(@PathVariable int id) {
        return eventService.deleteEvent(id);
    }

    @PostMapping("/api/events/{id}/reservations")
    @PreAuthorize("hasAuthority('USER')")
    public String bookSeat(@PathVariable int id) {
        return eventService.bookSeat(id);
    }
}

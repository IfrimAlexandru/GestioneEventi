package it.nextdevs.GestioneEventi.service;


import it.nextdevs.GestioneEventi.dto.EventDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.excepion.NotFoundException;
import it.nextdevs.GestioneEventi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public String saveEvent(EventDTO eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.getTitle());
        event.setDescription(eventDto.getDescription());
        event.setDate(eventDto.getDate());
        event.setPlace(eventDto.getPlace());
        event.setAvailableSeats(eventDto.getAvailableSeats());

        eventRepository.save(event);
        return "Event with id: " + event.getId() + " correctly saved";
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(int id) {
        return eventRepository.findById(id);
    }

    public String updateEvent(int id, EventDTO eventDto) {
        Optional<Event> eventOptional = getEventById(id);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setTitle(eventDto.getTitle());
            event.setDescription(eventDto.getDescription());
            event.setDate(eventDto.getDate());
            event.setPlace(eventDto.getPlace());
            event.setAvailableSeats(eventDto.getAvailableSeats());

            eventRepository.save(event);
            return "Event with id: " + id + " updated successfully";
        } else {
            throw new NotFoundException("Event with id: " + id + " not found");
        }
    }

    public String deleteEvent(int id) {
        Optional<Event> eventOptional = getEventById(id);

        if (eventOptional.isPresent()) {
            eventRepository.deleteById(id);
            return "Event with id: " + id + " deleted successfully";
        } else {
            throw new NotFoundException("Event with id: " + id + " not found");
        }
    }

    public String bookSeat(int id) {
        Optional<Event> eventOptional = getEventById(id);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (event.getAvailableSeats() > 0) {
                event.setAvailableSeats(event.getAvailableSeats() - 1);
                eventRepository.save(event);
                return "Seat successfully booked for event with id: " + id;
            } else {
                throw new NotFoundException("There are no seats available for the event with id: " + id);
            }
        }
        else {
            throw new NotFoundException("Event with id: " + id + " not found");
        }
    }
}
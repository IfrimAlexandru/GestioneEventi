package nextDevs.GestioneEventi.service;


import nextDevs.GestioneEventi.dto.EventoDto;
import nextDevs.GestioneEventi.entity.Evento;
import nextDevs.GestioneEventi.excepion.NotFoundException;
import nextDevs.GestioneEventi.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public String saveEvento(EventoDto eventoDto) {
        Evento evento = new Evento();
        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setData(eventoDto.getData());
        evento.setLuogo(eventoDto.getLuogo());
        evento.setPostiDisponibili(eventoDto.getPostiDisponibili());

        eventoRepository.save(evento);
        return "Evento with id=" + evento.getId() + " correctly saved";
    }

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> getEventoById(int id) {
        return eventoRepository.findById(id);
    }

    public String updateEvento(int id, EventoDto eventoDto) {
        Optional<Evento> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            Evento evento = eventoOptional.get();
            evento.setTitolo(eventoDto.getTitolo());
            evento.setDescrizione(eventoDto.getDescrizione());
            evento.setData(eventoDto.getData());
            evento.setLuogo(eventoDto.getLuogo());
            evento.setPostiDisponibili(eventoDto.getPostiDisponibili());

            eventoRepository.save(evento);
            return "Evento with id=" + id + " updated successfully";
        } else {
            throw new NotFoundException("Evento with id=" + id + " not found");
        }
    }

    public String deleteEvento(int id) {
        Optional<Evento> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            eventoRepository.deleteById(id);
            return "Evento with id=" + id + " deleted successfully";
        } else {
            throw new NotFoundException("Evento with id=" + id + " not found");
        }
    }

    public String prenotaPosto(int id) {
        Optional<Evento> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            Evento evento = eventoOptional.get();
            if (evento.getPostiDisponibili() > 0) {
                evento.setPostiDisponibili(evento.getPostiDisponibili() - 1);
                eventoRepository.save(evento);
                return "Posto prenotato con successo per l'evento con id=" + id;
            } else {
                throw new NotFoundException("Non ci sono posti disponibili per l'evento con id=" + id);
            }
        }
        else {
            throw new NotFoundException("Evento with id=" + id + " not found");
        }
    }
}
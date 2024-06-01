package nextDevs.GestioneEventi.service;


import nextDevs.GestioneEventi.dto.UtenteDto;
import nextDevs.GestioneEventi.entity.Evento;
import nextDevs.GestioneEventi.entity.Utente;
import nextDevs.GestioneEventi.enums.Role;
import nextDevs.GestioneEventi.excepion.BadRequestException;
import nextDevs.GestioneEventi.excepion.NotFoundException;
import nextDevs.GestioneEventi.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    public String saveUtente(UtenteDto utenteDto) {

        if(getUtenteByEmail(utenteDto.getEmail()).isEmpty()) {
            Utente utente = new Utente();
            utente.setName(utenteDto.getName());
            utente.setSurname(utenteDto.getName());
            utente.setSurname(utenteDto.getSurname());
            utente.setEmail(utenteDto.getEmail());
            utente.setRole(Role.USER);
            utente.setPassword( passwordEncoder.encode(utenteDto.getPassword()));

            sendMail(utente.getEmail());
            utenteRepository.save(utente);
            return "Utente with id=" + utente.getId() + "correctly saved";
        }else{
            throw new BadRequestException("Utente con email "+utenteDto.getEmail()+" già esistente");
        }
    }

    public List<Utente> getAllUtentes() {
        return utenteRepository.findAll();
    }

    public Optional <Utente> getUtenteById(int id) {
        return utenteRepository.findById(id);
    }

    public Utente updateUtente(int id, UtenteDto utenteDto) {
        Optional<Utente> utente = getUtenteById(id);

        if(utente.isPresent()) {
            Utente u = utente.get();
            u.setName(utenteDto.getName());
            u.setSurname(utenteDto.getSurname());
            u.setEmail(utenteDto.getEmail());

            u.setPassword( passwordEncoder.encode(utenteDto.getPassword()));

            return utenteRepository.save(u);

        }else {
           throw new NotFoundException("Utente with id= " + id + " not found");
        }
    }

    public String deleteUtente(int id) {
        Optional<Utente> utenteOptional = getUtenteById(id);

        if(utenteOptional.isPresent()) {
         utenteRepository.deleteById(id);
            return "Utente with id= " + id + " correctly deleted";
        }else {
            throw new NotFoundException("Utente with id= " + id + " not found");
        }
    }

    public Optional <Utente> getUtenteByEmail(String email) {
        Optional<Utente> utenteOptional= utenteRepository.findByEmail(email);

            return utenteOptional;

    }

    public List<Evento> getEventiPrenotati(int id) {
        Optional<Utente> utenteOptional = getUtenteById(id);
        if (utenteOptional.isPresent()) {
            return utenteOptional.get().getEventiPrenotati();
        } else {
            throw new NotFoundException("Utente with id=" + id + " not found");
        }
    }

    public String annullaPrenotazione(int utenteId, int eventoId) {
        Optional<Utente> utenteOptional = getUtenteById(utenteId);
        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            utente.annullaPrenotazione(eventoId);
            utenteRepository.save(utente);
            return "Prenotazione annullata con successo";
        } else {
            throw new NotFoundException("Utente with id=" + utenteId + " not found");
        }
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registrazione Servizio rest");//oggetto dell' email
        message.setText("Registrazione al servizio rest avvenuta con successo");//corpo dell'email

        javaMailSender.send(message);
    }

}

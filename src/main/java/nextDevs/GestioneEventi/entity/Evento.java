package nextDevs.GestioneEventi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue
    private int id;
    private String titolo;
    private String descrizione;
    private LocalDateTime data;
    private String luogo;
    private int postiDisponibili;

    @ManyToMany(mappedBy = "eventiPrenotati")
    private List<Utente> partecipanti = new ArrayList<>();
}


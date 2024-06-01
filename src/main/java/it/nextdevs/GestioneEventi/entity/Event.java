package it.nextdevs.GestioneEventi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String description;
    private LocalDateTime date;
    private String place;
    private int availableSeats;

    @ManyToMany(mappedBy = "bookedEvents")
    private List<User> participants = new ArrayList<>();
}


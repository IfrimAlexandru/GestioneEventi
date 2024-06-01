package it.nextdevs.GestioneEventi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    @NotBlank(message = "The title cannot be blank")
    private String title;

    @NotBlank(message = "The description cannot be blank")
    private String description;

    @FutureOrPresent(message = "The event date must be in the future or the same as the actual date")
    private LocalDateTime date;

    @NotBlank(message = "The place cannot be blank")
    private String place;

    private int availableSeats;
}

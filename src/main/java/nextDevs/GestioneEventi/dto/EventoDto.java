package nextDevs.GestioneEventi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventoDto {
    @NotBlank(message = "Il titolo non può essere vuoto o composto da soli spazi")
    private String titolo;

    @NotBlank(message = "La descrizione non può essere vuota o composta da soli spazi")
    private String descrizione;

    @FutureOrPresent(message = "La data dell'evento deve essere nel futuro o uguale alla data attuale")
    private LocalDateTime data;

    @NotBlank(message = "Il luogo non può essere vuoto o composto da soli spazi")
    private String luogo;

    private int postiDisponibili;
}

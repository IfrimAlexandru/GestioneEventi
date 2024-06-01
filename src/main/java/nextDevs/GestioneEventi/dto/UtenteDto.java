package nextDevs.GestioneEventi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteDto {

    @NotBlank(message = "Il nome non può essere vuoto o composto da soli spazi")
    private String name;

    @NotBlank(message = "Il cognome non può essere vuoto o composto da soli spazi")
    private String surname;

    @Email(message = "L'email non può essere vuota o composta da soli spazi")
    @NotBlank(message = "L'email non può essere vuota o composta da soli spazi")
    private String email;

    @NotBlank(message = "La password non può essere vuota o composta da soli spazi")
    private String password;
}

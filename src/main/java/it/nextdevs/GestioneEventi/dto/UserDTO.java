package it.nextdevs.GestioneEventi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "The name cannot be blank")
    private String name;

    @NotBlank(message = "The surname cannot be blank")
    private String surname;

    @Email(message = "The email cannot be blank")
    @NotBlank(message = "The email cannot be blank")
    private String email;

    @NotBlank(message = "The password cannot be blank")
    private String password;
}

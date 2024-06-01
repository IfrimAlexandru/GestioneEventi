package it.nextdevs.GestioneEventi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    @Email(message = "The email cannot be blank")
    @NotBlank
    private String email;
    @NotBlank(message = "The password cannot be blank")
    private String password;
}

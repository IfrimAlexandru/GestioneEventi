package it.nextdevs.GestioneEventi.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
public class Error {
    private String message;
    private LocalDate errorDate;
    private HttpStatus errorStatus;
}
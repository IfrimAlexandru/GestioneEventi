package nextDevs.GestioneEventi.service;

import nextDevs.GestioneEventi.dto.UtenteLoginDto;
import nextDevs.GestioneEventi.entity.Utente;
import nextDevs.GestioneEventi.excepion.UnauthorizedException;
import nextDevs.GestioneEventi.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
  private UtenteService utenteService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTool jwtTool;

    public String authenticateUtenteAndCreateToken(UtenteLoginDto utenteLoginDto) {
        Optional <Utente> utenteOptionalOptional = utenteService.getUtenteByEmail(utenteLoginDto.getEmail());
        if (utenteOptionalOptional.isEmpty()) {
            throw new UnauthorizedException("Error in authorization, relogin!");
        }else{
            Utente utente = utenteOptionalOptional.get();
            if (passwordEncoder.matches(utenteLoginDto.getPassword(), utente.getPassword())){
                return jwtTool.createToken(utente);
            }else {
                throw new UnauthorizedException("Error in authorization, relogin!");
            }
        }


    }

}



package it.nextdevs.GestioneEventi.service;

import it.nextdevs.GestioneEventi.dto.UserLoginDTO;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.excepion.UnauthorizedException;
import it.nextdevs.GestioneEventi.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
  private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTool jwtTool;

    public String authenticateUserAndCreateToken(UserLoginDTO userLoginDTO) {
        Optional <User> userOptional = userService.getUserByEmail(userLoginDTO.getEmail());
        if (userOptional.isEmpty()) {
            throw new UnauthorizedException("Error in authorization, re-login!");
        }else{
            User user = userOptional.get();
            if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())){
                return jwtTool.createToken(user);
            }else {
                throw new UnauthorizedException("Error in authorization, re-login!");
            }
        }


    }

}



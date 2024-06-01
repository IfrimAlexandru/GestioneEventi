package it.nextdevs.GestioneEventi.controller;




import it.nextdevs.GestioneEventi.dto.UserDTO;
import it.nextdevs.GestioneEventi.dto.UserLoginDTO;
import it.nextdevs.GestioneEventi.excepion.BadRequestException;
import it.nextdevs.GestioneEventi.service.AuthService;
import it.nextdevs.GestioneEventi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public String register(@RequestBody @Validated UserDTO userDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).
                    reduce("", (s, s2) -> s+s2));
        }

        return userService.saveUser(userDTO);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated UserLoginDTO userLoginDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).
                    reduce("", (s, s2) -> s+s2));
        }

        return authService.authenticateUserAndCreateToken(userLoginDTO);
    }





}

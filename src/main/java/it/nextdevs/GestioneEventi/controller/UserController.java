package it.nextdevs.GestioneEventi.controller;



import it.nextdevs.GestioneEventi.dto.UserDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.excepion.NotFoundException;
import it.nextdevs.GestioneEventi.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/api/users")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


    @GetMapping("/api/users/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER','MANAGER')")
    public User getUserById(@PathVariable int id){
        Optional<User> userOptional = userService.getUserById(id);

        if(userOptional.isPresent()){
            return userOptional.get();
        }
        else{
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }


    @PutMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public User updateUser(@PathVariable int id, @RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) throws BadRequestException {
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error->error.getDefaultMessage()).
                    reduce("", (s, s2) -> s+s2));
        }
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }

    @GetMapping("/api/users/{id}/booked-events")
    @PreAuthorize("hasAuthority('USER')")
    public List<Event> getBookedEvents(@PathVariable int id) {
        return userService.getBookerEvents(id);
    }

    @DeleteMapping("/api/users/{userId}/events/{eventId}/cancel-reservation")
    @PreAuthorize("hasAuthority('USER')")
    public String cancelReservation(@PathVariable int userId, @PathVariable int eventId) {
        return userService.cancelReservation(userId, eventId);
    }
}

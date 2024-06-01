package it.nextdevs.GestioneEventi.service;


import it.nextdevs.GestioneEventi.dto.UserDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.enums.Role;
import it.nextdevs.GestioneEventi.excepion.BadRequestException;
import it.nextdevs.GestioneEventi.excepion.NotFoundException;
import it.nextdevs.GestioneEventi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    public String saveUser(UserDTO userDTO) {

        if(getUserByEmail(userDTO.getEmail()).isEmpty()) {
            User user = new User();
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setEmail(userDTO.getEmail());
            user.setRole(Role.USER);
            user.setPassword( passwordEncoder.encode(userDTO.getPassword()));

            sendMail(user.getEmail());
            userRepository.save(user);
            return "User with id: " + user.getId() + "correctly saved";
        }else{
            throw new BadRequestException("User con email: "+ userDTO.getEmail()+" already exists");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional <User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User updateUser(int id, UserDTO userDTO) {
        Optional<User> user = getUserById(id);

        if(user.isPresent()) {
            User u = user.get();
            u.setName(userDTO.getName());
            u.setSurname(userDTO.getSurname());
            u.setEmail(userDTO.getEmail());

            u.setPassword( passwordEncoder.encode(userDTO.getPassword()));

            return userRepository.save(u);

        }else {
           throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    public String deleteUser(int id) {
        Optional<User> userOptional= getUserById(id);

        if(userOptional.isPresent()) {
         userRepository.deleteById(id);
            return "User with id: " + id + " correctly deleted";
        }else {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    public Optional <User> getUserByEmail(String email) {
        Optional<User> userOptional= userRepository.findByEmail(email);
        return userOptional;
    }

    public List<Event> getBookerEvents(int id) {
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isPresent()) {
            return userOptional.get().getBookedEvents();
        } else {
            throw new NotFoundException("User with id: " + id + " not found");
        }
    }

    public String cancelReservation(int userId, int eventId) {
        Optional<User> userOptional = getUserById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.cancelReservation(eventId);
            userRepository.save(user);
            return "Booking canceled successfully";
        } else {
            throw new NotFoundException("User with id: " + userId + " not found");
        }
    }

    private void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Registration for the rest Service");
        message.setText("Registration for the rest service was successful");

        javaMailSender.send(message);
    }

}

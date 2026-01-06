package io.paideia.backend.services;


import io.paideia.backend.entities.User;
import io.paideia.backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaideiaService {
    private final UserRepository userRepository;

    public PaideiaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String hashedPassword, String email) {
        return userRepository.save(new User(username, hashedPassword, email));
    }

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}

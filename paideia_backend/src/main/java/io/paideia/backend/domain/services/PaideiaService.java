package io.paideia.backend.domain.services;


import io.paideia.backend.domain.entities.User;
import io.paideia.backend.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PaideiaService {
    private UserRepository userRepository;

    public PaideiaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String hashedPassword, String email) {
        return userRepository.save(new User(username, hashedPassword, email));
    }


}

package io.paideia.backend.services;


import io.paideia.backend.entities.UserEntity;
import io.paideia.backend.exceptions.UserAlreadyExistsException;
import io.paideia.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.getUser(username).map(
                userEntity -> {
                    UserBuilder userBuilder = User
                            .withUsername(userEntity.getUsername())
                            .password(userEntity.getPasswordHash());
                    
                    //TODO change "admin" to an external property name to be retrieved from config
                    //TODO create the "admin" user at application startup (to prevent accidental registration)
                    if (userEntity.getUsername().equals("admin")) {
                        userBuilder.roles("USER", "ADMIN");
                    } else {
                        userBuilder.roles("USER");
                    }

                    return userBuilder.build();
                }
        ).orElseThrow(() -> new UsernameNotFoundException(username));
    }



    public UserEntity registerUser(String username, String rawPassword, String email) {
        //TODO ajouter une validation et mapper sur des exceptions dans le ControllerAdvice
        userRepository.findByUsername(username).ifPresent(user -> {
                throw new UserAlreadyExistsException(user.getUsername());
        });

        String hashedPassword = passwordEncoder.encode(rawPassword);
        return userRepository.save(new UserEntity(username, hashedPassword, email));
    }

    public Optional<UserEntity> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}

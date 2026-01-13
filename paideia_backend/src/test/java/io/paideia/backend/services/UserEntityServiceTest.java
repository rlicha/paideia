package io.paideia.backend.services;

import io.paideia.backend.ContainersConfiguration;
import io.paideia.backend.entities.UserEntity;
import io.paideia.backend.exceptions.UserAlreadyExistsException;
import io.paideia.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ContainersConfiguration.class)
class UserEntityServiceTest {

    @Autowired
    UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, new BCryptPasswordEncoder());
        userRepository.deleteAll();
    }

    @Test
    public void service_whenRegisteringCorrectUser_thenInsertsInDatabase() {
        userService.registerUser("John", "abc", "john@example.com");
        userService.registerUser("Alice", "efg", "alice@you.com");

        List<UserEntity> userEntities = userRepository.findAll();

        assertThat(userEntities).hasSize(2)
                .map(UserEntity::getUsername)
                .containsExactly("John", "Alice");
    }

    @Test
    public void service_whenRegisteringAlreadyExistingUser_thenIssue409Exception() {
        userService.registerUser("John", "abc", "john@example.com");

        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> {
                    userService.registerUser("John", "abc", "john@example.com");
                })
                .withMessageContaining("User John already exists.");
    }
}
package io.paideia.backend.services;

import io.paideia.backend.ContainersConfiguration;
import io.paideia.backend.entities.User;
import io.paideia.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ContainersConfiguration.class)
class PaideiaServiceTest {

    @Autowired
    UserRepository userRepository;

    private PaideiaService paideiaService;

    @BeforeEach
    void setUp() {
        paideiaService = new PaideiaService(userRepository);
        userRepository.deleteAll();
    }

    @Test
    public void service_whenRegisteringCorrectUser_thenInsertsInDatabase() {
        paideiaService.registerUser("John", "abc", "john@example.com");
        paideiaService.registerUser("Alice", "efg", "alice@you.com");

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2)
                .map(User::getUsername)
                .containsExactly("John", "Alice");
    }
}
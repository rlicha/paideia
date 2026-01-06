package io.paideia.backend.repositories;

import io.paideia.backend.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);
}

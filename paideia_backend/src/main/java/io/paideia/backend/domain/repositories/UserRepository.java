package io.paideia.backend.domain.repositories;

import io.paideia.backend.domain.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);
}

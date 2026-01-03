package io.paideia.backend.domain.repositories;

import io.paideia.backend.domain.entities.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.UUID;

public interface UserRepository extends ListCrudRepository<User, UUID> {

}

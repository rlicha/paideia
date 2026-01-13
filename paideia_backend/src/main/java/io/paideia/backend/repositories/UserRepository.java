package io.paideia.backend.repositories;

import io.paideia.backend.entities.UserEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends ListCrudRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

}

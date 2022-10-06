package com.github.mysterix5.template.security;

import com.github.mysterix5.template.model.security.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMongoRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
}

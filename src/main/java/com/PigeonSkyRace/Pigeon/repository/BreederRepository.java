package com.PigeonSkyRace.Pigeon.repository;

import com.PigeonSkyRace.Pigeon.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BreederRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
}

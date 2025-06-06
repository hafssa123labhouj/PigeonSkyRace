package com.PigeonSkyRace.Pigeon.repository;

import com.PigeonSkyRace.Pigeon.model.Saison;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SaisonRepository extends MongoRepository<Saison, String> {
    Saison findSaisonByName(String saisonId);
}

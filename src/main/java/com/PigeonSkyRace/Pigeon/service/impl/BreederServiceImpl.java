package com.PigeonSkyRace.Pigeon.service.impl;

import com.PigeonSkyRace.Pigeon.model.User;
import com.PigeonSkyRace.Pigeon.repository.BreederRepository;
import com.PigeonSkyRace.Pigeon.service.BreederService;
import com.PigeonSkyRace.Pigeon.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BreederServiceImpl implements BreederService {

    private final Logger logger = Logger.getLogger(BreederServiceImpl.class.getName());

    @Autowired
    private BreederRepository breederRepository;

    @Override
    public User createBreeder(User breeder) {
        String hashedPassword = PasswordUtil.hashPassword(breeder.getPassword());
        breeder.setPassword(hashedPassword);
        return breederRepository.save(breeder);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return breederRepository.findByEmail(email);
    }

    @Override
    public User getBreederById(String breederId) {
        return breederRepository.findById(breederId)
                .orElseThrow(() -> new IllegalArgumentException("no breeder found with id: " + breederId));
    }
}

package com.PigeonSkyRace.Pigeon.service.impl;

import com.PigeonSkyRace.Pigeon.model.Saison;
import com.PigeonSkyRace.Pigeon.repository.SaisonRepository;
import com.PigeonSkyRace.Pigeon.service.SaisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaisonServiceImpl implements SaisonService {
    @Autowired
    private SaisonRepository saisonRepository;
    @Override
    public Saison getSaison(String name) {
        return saisonRepository.findSaisonByName(name);
    }

    @Override
    public Saison addSaison(Saison saison) {
        return saisonRepository.save(saison);
    }
}

package com.PigeonSkyRace.Pigeon.service;

import com.PigeonSkyRace.Pigeon.model.Saison;

public interface SaisonService {
    Saison getSaison(String name);
    Saison addSaison(Saison saison);
}

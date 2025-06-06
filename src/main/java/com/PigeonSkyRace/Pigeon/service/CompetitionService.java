package com.PigeonSkyRace.Pigeon.service;

import com.PigeonSkyRace.Pigeon.model.Competition;
import com.PigeonSkyRace.Pigeon.model.Result;

import java.util.Optional;

public interface CompetitionService {
    Competition addCompetition(Competition competition);
    Optional<Result> updateCompetition(String id, String badge);
    Competition getCompetitionById(String competitionId);
    Competition closeCompetition(String competitionId);
}

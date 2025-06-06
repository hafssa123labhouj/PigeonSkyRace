package com.PigeonSkyRace.Pigeon.controller;

import com.PigeonSkyRace.Pigeon.dto.CompetitionRequest;
import com.PigeonSkyRace.Pigeon.dto.RaceData;
import com.PigeonSkyRace.Pigeon.model.Competition;
import com.PigeonSkyRace.Pigeon.model.Result;
import com.PigeonSkyRace.Pigeon.model.Saison;
import com.PigeonSkyRace.Pigeon.service.CompetitionService;
import com.PigeonSkyRace.Pigeon.service.ResultIService;
import com.PigeonSkyRace.Pigeon.service.SaisonService;
import com.PigeonSkyRace.Pigeon.util.CsvParserUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/organizer")
public class OrganizerController {

    @Autowired
    private CompetitionService competitionService;

    @Autowired
    private ResultIService resultService;
    @Autowired
    private SaisonService saisonService;

    private ResponseEntity<String> validateUser(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        if (userId == null || !Objects.equals(role, "organizer")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing user id or incorrect role");
        }
        return null;
    }

    @PostMapping("/addSaison")
    public ResponseEntity<?> addSaison(HttpServletRequest request, @Valid @RequestBody Saison saison, BindingResult result) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Saison savedSaison = saisonService.addSaison(saison);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSaison);

    }

    @PostMapping("/addCompetition")
    public ResponseEntity<?> addCompetition(HttpServletRequest request, @Valid @RequestBody CompetitionRequest competitionRequest, BindingResult result) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }

        Saison saison = saisonService.getSaison(competitionRequest.getSaisonName());
        if (saison == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saison not found");
        }
        competitionRequest.getCompetition().setIsOpen(true);
        competitionRequest.getCompetition().setSaisonId(saison.getId());

        Competition savedCompetition = competitionService.addCompetition(competitionRequest.getCompetition());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompetition);
    }

    @PutMapping("/{id}/addPigeonToCompetition")
    public ResponseEntity<?> updateCompetition(HttpServletRequest request, @PathVariable String id, @RequestParam String badge) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }

        if (!StringUtils.hasText(badge)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("pigeon badge is required");
        }

        Optional<Result> updatedCompetition = competitionService.updateCompetition(id, badge);
        if (updatedCompetition.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(updatedCompetition);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competition not found");
        }
    }

    @PostMapping("/{competitionId}/displayAllCompetitionResults")
    public ResponseEntity<?> displayAllCompetitionResults(HttpServletRequest request, @PathVariable String competitionId) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }
        List<Result> results = resultService.getCompetitionResults(competitionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(results);
    }

    @PostMapping("/{competitionId}/results")
    public ResponseEntity<?> uploadRaceData(HttpServletRequest request, @RequestParam("file")MultipartFile file, @PathVariable String competitionId) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }

        try {
            List<RaceData> raceDataList = CsvParserUtil.parseCsvFile(file);
            resultService.processRaceData(competitionId, raceDataList);
            return ResponseEntity.status(HttpStatus.OK).body(raceDataList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{competitionId}/closeCompetition")
    public ResponseEntity<?> closeCompetition(HttpServletRequest request, @PathVariable String competitionId) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }
        Competition competition = competitionService.closeCompetition(competitionId);
        if (competition == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competition not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(competition);
    }
}

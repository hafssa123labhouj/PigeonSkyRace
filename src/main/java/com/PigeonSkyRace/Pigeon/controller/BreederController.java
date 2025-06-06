package com.PigeonSkyRace.Pigeon.controller;

import com.PigeonSkyRace.Pigeon.model.Pigeon;
import com.PigeonSkyRace.Pigeon.model.Result;
import com.PigeonSkyRace.Pigeon.service.PigeonService;
import com.PigeonSkyRace.Pigeon.service.ResultIService;
import com.PigeonSkyRace.Pigeon.util.ExportResults;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/breeder")
public class BreederController {

    @Autowired
    private PigeonService pigeonService;

    @Autowired
    private ResultIService resultIService;

    private ResponseEntity<String> validateUser(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        String role = (String) request.getAttribute("role");

        if (userId == null || !Objects.equals(role, "breeder")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Missing user id or incorrect role");
        }
        return null;
    }

    @PostMapping("/addPigeon")
    public ResponseEntity<?> addPigeon(HttpServletRequest request, @Valid @RequestBody Pigeon pigeon, BindingResult result) {
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

        try {
            String userId = (String) request.getAttribute("userId");
            pigeon.setBreederId(userId);
            Pigeon savedPigeon = pigeonService.addPigeon(pigeon);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPigeon);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: The badge number must be unique.");
        }
    }

    @GetMapping("/getAllPigeons")
    public ResponseEntity<List<Pigeon>> getAllPigeons() {
        List<Pigeon> pigeons = pigeonService.getAllPigeons();
        return ResponseEntity.status(HttpStatus.OK).body(pigeons);
    }

    @GetMapping("/allResults")
    public ResponseEntity<?> getAllResults(HttpServletRequest request) {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }

        String userId = (String) request.getAttribute("userId");
        List<Result> results = resultIService.getAllBreederResults(userId);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/exportResults")
    public ResponseEntity<?> exportResults(HttpServletResponse response, HttpServletRequest request) throws DocumentException, IOException {
        ResponseEntity<String> validationResponse = validateUser(request);
        if (validationResponse != null) {
            return validationResponse;
        }

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=results_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        String userId = (String) request.getAttribute("userId");
        List<Result> results = resultIService.getAllBreederResults(userId);

        ExportResults exporter = new ExportResults(results);
        exporter.export(response);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }

    @GetMapping("/exportAllResults")
    public ResponseEntity<?> exportAllResults(HttpServletResponse response /* , HttpServletRequest request*/) throws DocumentException, IOException {
//        ResponseEntity<String> validationResponse = validateUser(request);
//        if (validationResponse != null) {
//            return validationResponse;
//        }

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=results_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Result> results = resultIService.getAllResults();

        ExportResults exporter = new ExportResults(results);
        exporter.export(response);
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}

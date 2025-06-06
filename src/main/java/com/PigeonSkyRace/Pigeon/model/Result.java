package com.PigeonSkyRace.Pigeon.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @Id
    private String id;

    private int points;

    private double speed;

    private int ranking;

    private double distance;

    private double flightTime;

    @NotNull(message = "Arrival date cannot be null")
    private LocalDateTime arrivalDate;

    @DocumentReference
    private Pigeon pigeon;

    @DocumentReference
    private Competition competition;

    public Result(double speed) {
        this.speed = speed;
        this.points = 0;
        this.ranking = 0;
        this.distance = 0.0;
        this.flightTime = 0.0;
        this.arrivalDate = LocalDateTime.now();
    }
}

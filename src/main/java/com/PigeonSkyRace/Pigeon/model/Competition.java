package com.PigeonSkyRace.Pigeon.model;

import com.PigeonSkyRace.Pigeon.model.enums.TypeEnum;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Competition {
    @Id
    private String id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Distance cannot be null")
    @Pattern(regexp = "^\\d+(\\.\\d+)?\\s?(km|m)$", message = "Distance must be in 'km' or 'm' format")
    private String distance;

    @NotNull(message = "Start date cannot be null")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDateTime startDate;

    @NotNull(message = "Longitude cannot be null")
    @Pattern(regexp = "^-?\\d{1,3}\\.\\d+$", message = "Invalid longitude format")
    private String longitude;

    @NotNull(message = "Latitude cannot be null")
    @Pattern(regexp = "^-?\\d{1,2}\\.\\d+$", message = "Invalid latitude format")
    private String latitude;

    private Boolean isOpen;

    @NotNull(message = "Type cannot be null")
    private TypeEnum type;

    private String saisonId;
}

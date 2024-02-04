package com.beaconfire.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error Details - Captures details about errors/exceptions")
public class ErrorDetails {

    @Schema(description = "Timestamp when the error occurred", example = "2023-01-01T12:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Error message detailing what went wrong", example = "Invalid request parameters")
    private String message;

    @Schema(description = "Additional details about the error, such as parameter names or invalid values", example = "The 'email' parameter is missing")
    private String details;

    // Lombok generates the constructor, getters, and setters
}


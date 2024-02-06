package com.beaconfire.userservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error Details - Captures details about errors/exceptions")
public class ErrorDetails {

    @Schema(description = "Timestamp when the error occurred", example = "2023-01-01T12:00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Error message detailing what went wrong", example = "Invalid request parameters")
    private String message;

}


package com.beaconfire.userservice.dto.UserAuthResponse;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class UserRegistrationResponse {

    @Schema(description = "Indicates if the registration was successful", example = "true")
    private boolean success;

    @Schema(description = "User's ID after successful registration", example = "1")
    private Long userId;

    @Schema(description = "Registration confirmation message", example = "User registered successfully.")
    private String message;
}


package com.beaconfire.userservice.dto.UserAuthResponse;
import com.beaconfire.userservice.domain.User;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class UserCreateResponse {

    @Schema(description = "Indicates if the registration was successful", example = "true")
    private boolean success;

    @Schema(description = "User object")
    private User user;

    @Schema(description = "Registration confirmation message", example = "User registered successfully.")
    private String message;
}


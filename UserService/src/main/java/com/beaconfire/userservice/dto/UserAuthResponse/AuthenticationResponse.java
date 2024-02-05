package com.beaconfire.userservice.dto.UserAuthResponse;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class AuthenticationResponse {

    @Schema(description = "Indicates if authentication was successful", example = "true")
    private boolean authenticated;

    @Schema(description = "User's ID", example = "123")
    private Long userId;

    @Schema(description = "User's hashed password", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String password;

    @Schema(description = "Authentication confirmation or error message", example = "Authentication successful.")
    private String message;
}

package com.beaconfire.userservice.dto.UserAuthResponse;

import com.beaconfire.userservice.domain.User;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class UserAuthenticationResponse {

    @Schema(description = "Indicates if authentication was successful", example = "true")
    private boolean authenticated;

    @Schema(description = "User object")
    private User user;

    @Schema(description = "Message indicating the result of the authentication", example = "Authentication successful")
    private String message;
}

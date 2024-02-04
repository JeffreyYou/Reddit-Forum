package com.beaconfire.userservice.dto.UserAuthResponse;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class ChangePasswordResponse {

    @Schema(description = "Indicates if the password change was successful", example = "true")
    private boolean success;

    @Schema(description = "Password change confirmation message", example = "Password updated successfully.")
    private String message;
}


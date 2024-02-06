package com.beaconfire.userservice.dto.UserAuthResponse;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class EmailVerificationResponse {

//    @Schema(description = "The ID of the user", example = "1")
//    private Long userId;

    @Schema(description = "Indicates if the email verification was successful", example = "true")
    private boolean verified;

    @Schema(description = "Email verification confirmation message", example = "Email verified successfully.")
    private String message;
}

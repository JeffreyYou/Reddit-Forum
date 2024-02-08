package com.beaconfire.userservice.dto.UserProfileResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
public class GetUserVerifiedResponse {
    @Schema(description = "Indicates if user is verified", example = "true")
    private boolean verified;

    @Schema(description = "Request confirmation message", example = "Action successfully.")
    private String message;
}

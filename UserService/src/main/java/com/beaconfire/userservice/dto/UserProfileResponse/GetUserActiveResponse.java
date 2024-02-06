package com.beaconfire.userservice.dto.UserProfileResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserActiveResponse {
    @Schema(description = "Indicates if user is verified", example = "true")
    private boolean activated;

    @Schema(description = "Request confirmation message", example = "Action successfully.")
    private String message;
}

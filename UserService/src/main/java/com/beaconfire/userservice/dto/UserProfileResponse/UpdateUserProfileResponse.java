package com.beaconfire.userservice.dto.UserProfileResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserProfileResponse {

    @Schema(description = "Indicates if the update was successful", example = "true")
    private boolean success;

    @Schema(description = "Response message", example = "User profile updated successfully")
    private String message;
}

package com.beaconfire.userservice.dto.UserProfileResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserProfileFieldResponse {

    @Schema(description = "Field name", example = "firstName")
    private String fieldName;

    @Schema(description = "Field value", example = "John")
    private String value;

    @Schema(description = "Response message", example = "Field value retrieved successfully")
    private String message;
}

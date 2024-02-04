package com.beaconfire.userservice.dto.UserProfileResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Response containing a list of user profiles")
public class UserProfileListResponse {

    @Schema(description = "List of user profiles")
    private List<UserProfileResponse> userProfiles;

    @Schema(description = "Additional message or description")
    private String message;
}

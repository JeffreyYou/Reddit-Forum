package com.beaconfire.userservice.dto.UserProfileResponse;

import com.beaconfire.userservice.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserProfileResponse {

    @Schema(description = "User object")
    private User user;

    @Schema(description = "Message indicating the result of the authentication", example = "Authentication successful")
    private String message;
}

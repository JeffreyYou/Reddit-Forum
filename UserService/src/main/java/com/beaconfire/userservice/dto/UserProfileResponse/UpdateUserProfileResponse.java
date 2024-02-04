package com.beaconfire.userservice.dto.UserProfileResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserProfileResponse {
    private boolean success;
    private String message;
}

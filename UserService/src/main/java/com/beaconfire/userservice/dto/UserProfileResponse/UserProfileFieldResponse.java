package com.beaconfire.userservice.dto.UserProfileResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileFieldResponse {
    private String fieldName;
    private String value;
    private String message;
}

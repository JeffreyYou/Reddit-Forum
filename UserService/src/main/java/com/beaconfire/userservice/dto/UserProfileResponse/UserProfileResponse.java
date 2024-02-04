package com.beaconfire.userservice.dto.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
public class UserProfileResponse {
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
    private Timestamp dateJoined;
    private String type;
    private String profileImageURL;
}

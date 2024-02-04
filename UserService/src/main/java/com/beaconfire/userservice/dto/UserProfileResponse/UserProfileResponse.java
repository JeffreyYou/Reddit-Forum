package com.beaconfire.userservice.dto.UserProfileResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserProfileResponse {

    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @Schema(description = "User's email address", example = "johndoe@example.com")
    private String email;

    @Schema(description = "Indicates if the user is active", example = "true")
    private boolean active;

    @Schema(description = "User's date joined", example = "2024-02-03T12:34:56Z")
    private Timestamp dateJoined;

    @Schema(description = "User's type (e.g., user, admin, super-admin)", example = "user")
    private String type;

    @Schema(description = "User's profile image URL", example = "https://example.com/profile.jpg")
    private String profileImageURL;

    @Schema(description = "Indicates if the user is verified by email", example = "true")
    private boolean verified;
}

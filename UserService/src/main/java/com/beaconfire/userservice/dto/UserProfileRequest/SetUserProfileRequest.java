package com.beaconfire.userservice.dto.UserProfileRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetUserProfileRequest {

    @NotBlank(message = "First name cannot be blank")
    @Schema(description = "User's first name", example = "John", required = true)
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Schema(description = "User's last name", example = "Doe", required = true)
    private String lastName;

    @Schema(description = "User's profile image URL", example = "https://example.com/profile.jpg")
    private String profileImageURL;
}

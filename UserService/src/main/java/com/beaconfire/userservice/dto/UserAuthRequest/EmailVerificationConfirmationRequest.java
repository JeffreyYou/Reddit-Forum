package com.beaconfire.userservice.dto.UserAuthRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationConfirmationRequest {

    @NotNull(message = "User ID cannot be null")
    @Schema(description = "The ID of the user", example = "1", required = true)
    private Long userId;

    @Email(message = "Invalid email format")
    @NotNull(message = "Email cannot be null")
    @Schema(description = "User's email address to be verified", example = "user@example.com", required = true)
    private String email;

    // Getters and Setters are provided by the @Data annotation from Lombok
}

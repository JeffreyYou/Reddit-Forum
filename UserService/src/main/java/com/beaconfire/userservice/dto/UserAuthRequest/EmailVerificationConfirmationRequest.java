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

    // Getters and Setters are provided by the @Data annotation from Lombok
}

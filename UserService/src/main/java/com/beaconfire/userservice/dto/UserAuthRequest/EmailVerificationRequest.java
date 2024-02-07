package com.beaconfire.userservice.dto.UserAuthRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationRequest {

    @NotNull(message = "Token cannot be null")
    @NotBlank(message = "Token cannot be blank")
    @Schema(description = "The token of the user", example = "2d9c2c41-962a-442b-af53-8a735d44b9a7", required = true)
    private String token;
}

package com.beaconfire.userservice.dto.UserAuthRequest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
    private String email;
}

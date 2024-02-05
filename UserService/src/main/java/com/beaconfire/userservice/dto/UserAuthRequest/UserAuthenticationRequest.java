package com.beaconfire.userservice.dto.UserAuthRequest;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthenticationRequest {

    @NotBlank(message = "Username cannot be blank")
    @Schema(description = "User's username for login", example = "john_doe", required = true)
    private Long userId;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "User's password for login", example = "securePassword123", required = true)
    private String password;
}

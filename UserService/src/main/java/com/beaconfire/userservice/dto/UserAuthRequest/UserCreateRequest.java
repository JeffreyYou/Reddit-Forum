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
public class UserCreateRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "User's password", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", required = true)
    private String password;

    /***** firstname and lastname (newly added) *****/
    @NotBlank(message = "Firstname cannot be blank")
    @Schema(description = "User's firstname", example = "john", required = true)
    private String firstname;

    @NotBlank(message = "Lastname cannot be blank")
    @Schema(description = "User's lastname", example = "doe", required = true)
    private String lastname;
}

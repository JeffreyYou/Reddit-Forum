package com.beaconfire.authservice.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegRequest {

    @Email(message = "Email must be in valid format")
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")
    String email;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    String password;

    @NotNull(message = "Firstname cannot be null")
    @NotBlank(message = "Firstname cannot be blank")
    String firstname;

    @NotNull(message = "Lastname cannot be null")
    @NotBlank(message = "Lirstname cannot be blank")
    @NotBlank
    String lastname;
}

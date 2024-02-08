package com.beaconfire.authservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RegisterRequest {

    @Email(message = "email should be valid")
    @NotBlank(message = "email cannot be blank")
    String email;
    @NotBlank(message = "password cannot be blank")
    String password;
}


package com.beaconfire.authservice.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegRequest {

    @NotNull(message = "Email cannot be null")
    String email;

    @NotNull(message = "Password cannot be null")
    String password;
}

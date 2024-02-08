package com.beaconfire.authservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginResponse {
    String message;
    String token;
}

package com.beaconfire.authservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegResponse {
    String message;
    String token;
}

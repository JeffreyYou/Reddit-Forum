package com.beaconfire.postandreplyservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserVerifiedResponse {
    private boolean verified;
    private String message;
}

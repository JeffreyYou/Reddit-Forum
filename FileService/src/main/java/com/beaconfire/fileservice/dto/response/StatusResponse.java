package com.beaconfire.fileservice.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StatusResponse {
    String status;
    String message;
    String key;
    String url;
}

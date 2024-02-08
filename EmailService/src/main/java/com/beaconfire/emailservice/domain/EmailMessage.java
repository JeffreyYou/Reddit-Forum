package com.beaconfire.emailservice.domain;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmailMessage {
    private String to;
    private String subject;
    private String body;
}

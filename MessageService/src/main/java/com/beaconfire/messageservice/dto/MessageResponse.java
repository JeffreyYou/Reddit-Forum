package com.beaconfire.messageservice.dto;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MessageResponse {
    private Timestamp dateCreated;
    private String email;
    private String message;
    private String status;
}


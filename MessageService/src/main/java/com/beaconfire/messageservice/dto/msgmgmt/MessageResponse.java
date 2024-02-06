package com.beaconfire.messageservice.dto.msgmgmt;


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


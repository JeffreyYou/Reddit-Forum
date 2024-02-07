package com.beaconfire.messageservice.dto.msgmgmt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@Schema(description = "Response object for a message")
public class MessageResponse {
    @Schema(description = "1")
    private Long messageId;

    @Schema(description = "Date created")
    private Timestamp dateCreated;

    @Schema(description = "Email address")
    private String email;

    @Schema(description = "Message content")
    private String message;

    @Schema(description = "Message status")
    private String status;
}

package com.beaconfire.messageservice.dto.msgmgmt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@Schema(description = "Response object for a list of messages")
public class MessageListResponse {
    @Schema(description = "List of messages")
    private List<MessageResponse> messagesList;

    @Schema(description = "Additional message or details about the response")
    private String message;
}

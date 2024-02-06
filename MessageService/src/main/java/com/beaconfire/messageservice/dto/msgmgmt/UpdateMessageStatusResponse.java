package com.beaconfire.messageservice.dto.msgmgmt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Response object for updating message status")
public class UpdateMessageStatusResponse {
    @Schema(description = "Indicates whether the update was successful", example = "true")
    private boolean success;

    @Schema(description = "Additional message or details about the response", example = "Message status updated successfully")
    private String message;
}

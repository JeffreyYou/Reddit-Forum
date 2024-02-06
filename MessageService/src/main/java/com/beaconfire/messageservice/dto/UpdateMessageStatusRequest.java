package com.beaconfire.messageservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@Schema(description = "Request object to update message status")
public class UpdateMessageStatusRequest {
    @NotBlank
    @Schema(description = "Message ID to update", required = true)
    private Long messageId;

    @NotBlank
    @Pattern(regexp = "^(Open|Closed)$", message = "Status must be 'Open' or 'Closed'")
    @Schema(description = "New status value ('Open' or 'Closed')", required = true, allowableValues = {"Open", "Closed"})
    private String newStatus;
}

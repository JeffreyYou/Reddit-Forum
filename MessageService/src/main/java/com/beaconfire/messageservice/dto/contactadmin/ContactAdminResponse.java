package com.beaconfire.messageservice.dto.contactadmin;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
public class ContactAdminResponse {
    @Schema(description = "Indicates whether the operation was successful", example = "true")
    private boolean success;

    @Schema(description = "A message providing additional information about the response")
    private String message;
}

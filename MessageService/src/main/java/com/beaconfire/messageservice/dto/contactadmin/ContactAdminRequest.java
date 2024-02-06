package com.beaconfire.messageservice.dto.contactadmin;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ContactAdminRequest {
    @NotBlank(message = "Subject cannot be blank")
    @Schema(description = "The subject of the contact request", required = true)
    private String subject;

    @NotBlank(message = "Email cannot be blank")
    @Schema(description = "The email of the person sending the request", required = true)
    private String email;

    @NotBlank(message = "Message cannot be blank")
    @Schema(description = "The message content of the contact request", required = true)
    private String message;
}

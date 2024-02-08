package com.beaconfire.userservice.dto.UserAuthResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmailResponse {

    @Schema(description = "Indicates if the email update was successful", example = "true")
    private boolean success;

    @Schema(description = "Email update confirmation message", example = "Email updated successfully.")
    private String message;
}

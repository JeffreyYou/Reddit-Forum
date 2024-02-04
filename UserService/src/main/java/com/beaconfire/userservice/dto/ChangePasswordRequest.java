package com.beaconfire.userservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Old password cannot be blank")
    @Schema(description = "User's current password", example = "currentPassword123", required = true)
    private String oldPassword;

    @NotBlank(message = "New password cannot be blank")
    @Schema(description = "User's new password", example = "newSecurePassword456", required = true)
    private String newPassword;
}

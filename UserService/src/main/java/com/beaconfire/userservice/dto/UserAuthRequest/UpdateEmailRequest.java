package com.beaconfire.userservice.dto.UserAuthRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmailRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Old email cannot be blank")
    @NotNull(message = "Old email cannot be null")
    @Schema(description = "User's old email", example = "john.doe@example.com", required = true)
    private String oldEmail;

    @Email(message = "Invalid email format")
    @NotBlank(message = "New email cannot be blank")
    @NotNull(message = "New email cannot be null")
    @Schema(description = "User's new email", example = "john.doe@example.com", required = true)
    private String newEmail;
}

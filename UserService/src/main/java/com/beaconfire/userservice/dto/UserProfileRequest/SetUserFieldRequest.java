package com.beaconfire.userservice.dto.UserProfileRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class SetUserFieldRequest {
    @NotBlank(message = "Field name cannot be blank")
    @Schema(description = "User field name to be set", example = "firstName", required = true)
    private String fieldName;

    @NotBlank(message = "Field value cannot be blank")
    @Schema(description = "User field value to be set", example = "Doe", required = true)
    private String fieldValue;

}

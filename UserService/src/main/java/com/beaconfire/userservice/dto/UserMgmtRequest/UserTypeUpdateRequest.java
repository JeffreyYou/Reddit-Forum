package com.beaconfire.userservice.dto.UserMgmtRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeUpdateRequest {

    @NotBlank(message = "Role type cannot be blank")
    @Schema(description = "User's type to be updated", example = "user", required = true)
    private String type;

}

package com.beaconfire.userservice.dto.UserMgmtResponse;

import com.beaconfire.userservice.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGeneralResponse {

    @Schema(description = "Indicates if the request action was successful", example = "true")
    private boolean success;;

    @Schema(description = "Request confirmation message", example = "Action successfully.")
    private String message;
}
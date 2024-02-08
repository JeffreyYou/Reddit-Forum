package com.beaconfire.userservice.dto.UserAuthResponse;

import com.beaconfire.userservice.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserListResponse {

    @Schema(description = "A list of users.")
    private List<User> userList;

    @Schema(description = "Registration confirmation message", example = "User registered successfully.")
    private String message;
}

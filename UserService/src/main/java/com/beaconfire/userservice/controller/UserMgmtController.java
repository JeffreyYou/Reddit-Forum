package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.dto.ErrorDetails;
import com.beaconfire.userservice.dto.UserAuthResponse.UserListResponse;
import com.beaconfire.userservice.dto.UserMgmtRequest.UserTypeUpdateRequest;
import com.beaconfire.userservice.dto.UserMgmtResponse.UserGeneralResponse;
import com.beaconfire.userservice.service.UserMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class UserMgmtController {

    private final UserMgmtService userMgmtService;

    @Autowired
    public UserMgmtController(UserMgmtService userService) {
        this.userMgmtService = userService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @GetMapping("")
    @Operation(summary = "Retrieves a list of all registered users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                            content = @Content(schema = @Schema(implementation = UserListResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<UserListResponse> getAllUsers() {
        return ResponseEntity.ok(UserListResponse.builder()
                .userList(userMgmtService.getAllUsers())
                .message("List of users retrieved successfully.")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @PatchMapping("/{userId}/roles")
    @Operation(summary = "Updates the roles of a specific user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User roles updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<UserGeneralResponse> updateUserRoles(@PathVariable Long userId, @RequestBody UserTypeUpdateRequest request) {
        userMgmtService.updateUserType(userId, request.getType());
        return ResponseEntity.ok(UserGeneralResponse.builder()
                .success(true)
                .message("User roles updated successfully.")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @PatchMapping("/{userId}/deactivate")
    @Operation(summary = "Deactivates a user's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User account deactivated successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<UserGeneralResponse> deactivateUser(@PathVariable Long userId) {
        userMgmtService.deactivateUser(userId);
        return ResponseEntity.ok(UserGeneralResponse.builder()
                .success(true)
                .message("User account deactivated successfully.")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @PatchMapping("/{userId}/activate")
    @Operation(summary = "Reactivates a user's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User account reactivated successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<UserGeneralResponse> activateUser(@PathVariable Long userId) {
        userMgmtService.activateUser(userId);
        return ResponseEntity.ok(UserGeneralResponse.builder()
                .success(true)
                .message("User account reactivated successfully.")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @GetMapping("/banned")
    @Operation(summary = "Lists all banned users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of banned users",
                    content = @Content(schema = @Schema(implementation = UserListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<UserListResponse> listBannedUsers() {
        return ResponseEntity.ok(UserListResponse.builder()
                .userList(userMgmtService.getAllBannedUsers())
                .message("List of banned users retrieved successfully.")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @GetMapping("/active")
    @Operation(summary = "Lists all active (not banned) users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of active users",
                    content = @Content(schema = @Schema(implementation = UserListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<UserListResponse> listActiveUsers() {
        return ResponseEntity.ok(UserListResponse.builder()
                .userList(userMgmtService.getAllActiveUsers())
                .message("List of active users retrieved successfully.")
                .build());
    }

}

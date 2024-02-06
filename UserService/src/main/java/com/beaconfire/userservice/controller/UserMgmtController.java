package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.domain.UserPage;
import com.beaconfire.userservice.dto.ErrorDetails;
import com.beaconfire.userservice.dto.UserMgmtRequest.UserTypeUpdateRequest;
import com.beaconfire.userservice.dto.UserMgmtResponse.UserGeneralResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileListResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileResponse;
import com.beaconfire.userservice.service.UserMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/user")
@CrossOrigin(origins = "*")
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
                            content = @Content(schema = @Schema(implementation = UserPage.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userMgmtService.getAllUsers());
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
                    content = @Content(schema = @Schema(implementation = UserProfileListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileListResponse> listBannedUsers() {
        List<UserProfileResponse> bannedUsers = convertToUserProfileResponses(userMgmtService.getAllBannedUsers());
        return ResponseEntity.ok(buildUserProfileListResponse(bannedUsers, "List of banned users retrieved successfully."));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @GetMapping("/active")
    @Operation(summary = "Lists all active (not banned) users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of active users",
                    content = @Content(schema = @Schema(implementation = UserProfileListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileListResponse> listActiveUsers() {
        List<UserProfileResponse> activeUsers = convertToUserProfileResponses(userMgmtService.getAllActiveUsers());
        return ResponseEntity.ok(buildUserProfileListResponse(activeUsers, "List of active users retrieved successfully."));
    }

    private List<UserProfileResponse> convertToUserProfileResponses(List<User> users) {
        return users.stream()
                .map(user -> UserProfileResponse.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .active(user.isActive())
                        .dateJoined(user.getDateJoined())
                        .type(user.getType())
                        .verified(user.isVerified())
                        .profileImageURL(user.getProfileImageURL())
                        .build())
                .collect(Collectors.toList());
    }

    private UserProfileListResponse buildUserProfileListResponse(List<UserProfileResponse> userProfiles, String message) {
        return UserProfileListResponse.builder()
                .userProfiles(userProfiles)
                .message(message)
                .build();
    }

}

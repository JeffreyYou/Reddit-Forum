package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.dto.ErrorDetails;
import com.beaconfire.userservice.dto.UserMgmtResponse.UserGeneralResponse;
import com.beaconfire.userservice.dto.UserProfileRequest.SetUserFieldRequest;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserActiveResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserProfileFieldResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserProfileResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserVerifiedResponse;
import com.beaconfire.userservice.service.UserAuthService;
import com.beaconfire.userservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserAuthService userAuthService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService, UserAuthService userAuthService) {
        this.userProfileService = userProfileService;
        this.userAuthService = userAuthService;
    }

    @GetMapping("/status")
    @Operation(summary = "Retrieves the status (banned or active) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user status",
                    content = @Content(schema = @Schema(implementation = GetUserActiveResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserActiveResponse> getUserStatus() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserActiveResponse.builder()
                .activated(userProfileService.findUserById(userId).isActive())
                .message("Return user status successfully.")
                .build());
    }

    @GetMapping("/type")
    @Operation(summary = "Retrieves the type (user, admin, super-admin) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user type",
                    content = @Content(schema = @Schema(implementation = GetUserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileFieldResponse> getUserType() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserProfileFieldResponse.builder()
                .fieldName("type")
                .value(userProfileService.findUserById(userId).getType())
                .message("Return user type successfully.")
                .build());
    }

    @GetMapping("/firstname")
    @Operation(summary = "Retrieves the first name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the first name",
                    content = @Content(schema = @Schema(implementation = GetUserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileFieldResponse> getUserFirstName() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserProfileFieldResponse.builder()
                .fieldName("firstName")
                .value(userProfileService.findUserById(userId).getFirstName())
                .message("Return user first name successfully.")
                .build());
    }

    @PostMapping("/firstname")
    @Operation(summary = "Updates the first name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the first name",
                    content = @Content(schema = @Schema(implementation = UserGeneralResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<UserGeneralResponse> updateUserFirstName(@RequestBody SetUserFieldRequest request) {
        final Long userId = getUserId();
        userProfileService.setUserField(userId, request.getFieldName(), request.getFieldValue());
        return ResponseEntity.ok(UserGeneralResponse.builder()
                .success(true)
                .message("User first name updated successfully.")
                .build());
    }

    @GetMapping("/lastname")
    @Operation(summary = "Retrieves the last name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the last name",
                    content = @Content(schema = @Schema(implementation = GetUserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileFieldResponse> getUserLastName() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserProfileFieldResponse.builder()
                .fieldName("lastName")
                .value(userProfileService.findUserById(userId).getLastName())
                .message("Return user last name successfully.")
                .build());
    }

    @PostMapping("/lastname")
    @Operation(summary = "Updates the last name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the last name",
                    content = @Content(schema = @Schema(implementation = UserGeneralResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<UserGeneralResponse> updateUserLastName(@RequestBody SetUserFieldRequest request) {
        final Long userId = getUserId();
        userProfileService.setUserField(userId, request.getFieldName(), request.getFieldValue());
        return ResponseEntity.ok(UserGeneralResponse.builder()
                .success(true)
                .message("User last name updated successfully.")
                .build());
    }

    @GetMapping("/email")
    @Operation(summary = "Retrieves the email of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the email",
                    content = @Content(schema = @Schema(implementation = GetUserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileFieldResponse> getUserEmail() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserProfileFieldResponse.builder()
                .fieldName("email")
                .value(userProfileService.findUserById(userId).getEmail())
                .message("Return user email successfully.")
                .build());
    }

    @GetMapping("/datejoined")
    @Operation(summary = "Retrieves the date joined of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the date joined",
                    content = @Content(schema = @Schema(implementation = GetUserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileFieldResponse> getUserDateJoined() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserProfileFieldResponse.builder()
                .fieldName("dateJoined")
                .value(userProfileService.findUserById(userId).getDateJoined().toString())
                .message("Return user date joined successfully.")
                .build());
    }

    @GetMapping("/profile-image-url")
    @Operation(summary = "Retrieves the profile image URL of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the profile image URL",
                    content = @Content(schema = @Schema(implementation = GetUserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileFieldResponse> getUserProfileImageURL() {
        final Long userId = getUserId();
        return ResponseEntity.ok(GetUserProfileFieldResponse.builder()
                .fieldName("profileImageUrl")
                .value(userProfileService.findUserById(userId).getProfileImageURL())
                .message("Return user profile image URL successfully.")
                .build());
    }

    @PostMapping("/profile-image-url")
    @Operation(summary = "Updates the profile image URL of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the profile image URL",
                    content = @Content(schema = @Schema(implementation = UserGeneralResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<UserGeneralResponse> updateUserProfileImageURL(@RequestBody SetUserFieldRequest request) {
        final Long userId = getUserId();
        userProfileService.setUserField(userId, request.getFieldName(), request.getFieldValue());
        return ResponseEntity.ok(UserGeneralResponse.builder()
                .success(true)
                .message("User profile image URL updated successfully.")
                .build());
    }

    @GetMapping("/verified")
    @Operation(summary = "Retrieves whether the user has been email verified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the verified",
                    content = @Content(schema = @Schema(implementation = GetUserVerifiedResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserVerifiedResponse> isUserVerified() {
        final Long userId = getUserId();
        return ResponseEntity.ok(
                GetUserVerifiedResponse.builder()
                        .verified(userProfileService.findUserById(userId).isVerified())
                        .message("Return user verified successfully.")
                        .build());
    }

    @GetMapping("/profile")
    @Operation(summary = "Retrieves the user profile (first name, last name, email, profile image URL) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user profile",
                    content = @Content(schema = @Schema(implementation = GetUserProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
    })
    public ResponseEntity<GetUserProfileResponse> getUserProfile() {
        final Long userId = getUserId();
        return ResponseEntity.ok(
                GetUserProfileResponse.builder()
                        .user(userProfileService.findUserById(userId))
                        .message("Return user profile successfully.")
                        .build());
    }

    private Long getUserId() {
        return userAuthService.getCurrentUserId();
    }
}

package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.UserProfileRequest.UpdateUserProfileRequest;
import com.beaconfire.userservice.dto.UserProfileResponse.UpdateUserProfileResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileFieldResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileListResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileResponse;
import com.beaconfire.userservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/banned")
    @Operation(summary = "Lists all banned users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of banned users",
                    content = @Content(schema = @Schema(implementation = UserProfileListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileListResponse> listBannedUsers() {
        List<UserProfileResponse> bannedUsers = convertToUserProfileResponses(userProfileService.getAllBannedUsers());
        return ResponseEntity.ok(buildUserProfileListResponse(bannedUsers, "List of banned users retrieved successfully."));
    }

    @GetMapping("/active")
    @Operation(summary = "Lists all active (not banned) users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of active users",
                    content = @Content(schema = @Schema(implementation = UserProfileListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileListResponse> listActiveUsers() {
        List<UserProfileResponse> activeUsers = convertToUserProfileResponses(userProfileService.getAllActiveUsers());
        return ResponseEntity.ok(buildUserProfileListResponse(activeUsers, "List of active users retrieved successfully."));
    }

    @GetMapping("/status/{userId}")
    @Operation(summary = "Retrieves the status (banned or active) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user status",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getUserStatus(@PathVariable Long userId) {
        String userStatus = userProfileService.getUserStatus(userId);
        if (userStatus != null) {
            return ResponseEntity.ok(userStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/role/{userId}")
    @Operation(summary = "Retrieves the type (user, admin, super-admin) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user type",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserType(@PathVariable Long userId) {
        String userType = userProfileService.getUserType(userId);
        return ResponseEntity.ok(UserProfileFieldResponse.builder()
                .fieldName("userType")
                .value(userType)
                .message("Return user type successfully.")
                .build());
    }

    @GetMapping("/firstName/{userId}")
    @Operation(summary = "Retrieves the first name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the first name",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserFirstName(@PathVariable Long userId) {
        String firstName = userProfileService.getUserFirstNameById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("firstName", firstName));
    }

    @GetMapping("/lastName/{userId}")
    @Operation(summary = "Retrieves the last name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the last name",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserLastName(@PathVariable Long userId) {
        String lastName = userProfileService.getUserLastNameById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("lastName", lastName));
    }

    @GetMapping("/email/{userId}")
    @Operation(summary = "Retrieves the email of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the email",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserEmail(@PathVariable Long userId) {
        String email = userProfileService.getUserEmailById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("email", email));
    }

    @GetMapping("/dateJoined/{userId}")
    @Operation(summary = "Retrieves the date joined of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the date joined",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserDateJoined(@PathVariable Long userId) {
        Timestamp dateJoined = userProfileService.getUserDateJoinedById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("dateJoined", dateJoined.toString()));
    }

    @GetMapping("/profileImageURL/{userId}")
    @Operation(summary = "Retrieves the profile image URL of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the profile image URL",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserProfileImageURL(@PathVariable Long userId) {
        String profileImageURL = userProfileService.getUserProfileImageURLById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("profileImageURL", profileImageURL));
    }

    @PostMapping("/profile/{userId}")
    @Operation(summary = "Updates the user profile (first name, last name, email, profile image URL) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user profile",
                    content = @Content(schema = @Schema(implementation = UpdateUserProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@PathVariable Long userId, @RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        boolean updated = userProfileService.updateUserProfileById(userId, updateUserProfileRequest);
        if (updated) {
            return ResponseEntity.ok(UpdateUserProfileResponse.builder()
                    .success(true)
                    .message("Successfully updated user profile!")
                    .build());
        } else {
            // to be modified
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "Retrieves the user profile (first name, last name, email, profile image URL) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user profile",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId) {
        UserProfileResponse userProfile = userProfileService.getUserProfileById(userId);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    private UserProfileFieldResponse buildUserProfileFieldResponse(String fieldName, String value) {
        return UserProfileFieldResponse.builder()
                .fieldName(fieldName)
                .value(value)
                .message("Return " + fieldName + " successfully.")
                .build();
    }

    private UserProfileListResponse buildUserProfileListResponse(List<UserProfileResponse> userProfiles, String message) {
        return UserProfileListResponse.builder()
                .userProfiles(userProfiles)
                .message(message)
                .build();
    }
}

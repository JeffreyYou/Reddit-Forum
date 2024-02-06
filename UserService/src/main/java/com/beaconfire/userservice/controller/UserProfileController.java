package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.UserProfileRequest.UpdateUserProfileRequest;
import com.beaconfire.userservice.dto.UserProfileResponse.UpdateUserProfileResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileFieldResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileListResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileResponse;
import com.beaconfire.userservice.dto.UserVerifiedResponse;
import com.beaconfire.userservice.service.UserAuthService;
import com.beaconfire.userservice.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getUserStatus() {
        Long userId = getUserId();
        String userStatus = userProfileService.getUserStatus(userId);
        if (userStatus != null) {
            return ResponseEntity.ok(userStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/type")
    @Operation(summary = "Retrieves the type (user, admin, super-admin) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user type",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserType() {
        Long userId = getUserId();
        String userType = userProfileService.getUserType(userId);
        return ResponseEntity.ok(UserProfileFieldResponse.builder()
                .fieldName("userType")
                .value(userType)
                .message("Return user type successfully.")
                .build());
    }

    @GetMapping("/firstname")
    @Operation(summary = "Retrieves the first name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the first name",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserFirstName() {
        Long userId = getUserId();
        String firstName = userProfileService.getUserFirstNameById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("firstName", firstName));
    }

    @GetMapping("/lastname")
    @Operation(summary = "Retrieves the last name of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the last name",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserLastName() {
        Long userId = getUserId();
        String lastName = userProfileService.getUserLastNameById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("lastName", lastName));
    }

    @GetMapping("/email")
    @Operation(summary = "Retrieves the email of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the email",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserEmail() {
        Long userId = getUserId();
        String email = userProfileService.getUserEmailById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("email", email));
    }

    @GetMapping("/datejoined")
    @Operation(summary = "Retrieves the date joined of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the date joined",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserDateJoined() {
        Long userId = getUserId();
        Timestamp dateJoined = userProfileService.getUserDateJoinedById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("dateJoined", dateJoined.toString()));
    }

    @GetMapping("/profileimageurl")
    @Operation(summary = "Retrieves the profile image URL of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the profile image URL",
                    content = @Content(schema = @Schema(implementation = UserProfileFieldResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileFieldResponse> getUserProfileImageURL() {
        Long userId = getUserId();
        String profileImageURL = userProfileService.getUserProfileImageURLById(userId);
        return ResponseEntity.ok(buildUserProfileFieldResponse("profileImageURL", profileImageURL));
    }

    @GetMapping("/verified")
    @Operation(summary = "Retrieves whether the user has been email verified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the verified",
                    content = @Content(schema = @Schema(implementation = UserVerifiedResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserVerifiedResponse> getUserVerified() {
        Long userId = getUserId();
        Boolean verified = userProfileService.getUserVerifiedById(userId);
        return new ResponseEntity<>(UserVerifiedResponse.builder().verified(verified).build(), HttpStatus.OK);
    }

    @PostMapping("/profile")
    @Operation(summary = "Updates the user profile (first name, last name, email, profile image URL) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user profile",
                    content = @Content(schema = @Schema(implementation = UpdateUserProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UpdateUserProfileResponse> updateUserProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        Long userId = getUserId();
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

    @GetMapping("/profile")
    @Operation(summary = "Retrieves the user profile (first name, last name, email, profile image URL) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user profile",
                    content = @Content(schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserProfileResponse> getUserProfile() {
        Long userId = getUserId();
        UserProfileResponse userProfile = userProfileService.getUserProfileById(userId);
        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private UserProfileFieldResponse buildUserProfileFieldResponse(String fieldName, String value) {
        return UserProfileFieldResponse.builder()
                .fieldName(fieldName)
                .value(value)
                .message("Return " + fieldName + " successfully.")
                .build();
    }

    private Long getUserId() {
        return userAuthService.getCurrentUserId();
    }
}

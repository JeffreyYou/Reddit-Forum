package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.ErrorDetails;
import com.beaconfire.userservice.dto.UserAuthRequest.ChangePasswordRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.EmailVerificationConfirmationRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserAuthenticationRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserCreateRequest;
import com.beaconfire.userservice.dto.UserAuthResponse.UserAuthenticationResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.ChangePasswordResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.EmailVerificationResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.UserCreateResponse;

import com.beaconfire.userservice.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    private final UserAuthService userAuthService;

    @Autowired
    public UserAuthController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @PostMapping("/user/create")
    @Operation(summary = "Create a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully create the user",
                            content = @Content(schema = @Schema(implementation = UserCreateResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid user create details",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))) // Assuming an ErrorDetails schema for error responses
            })
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest) {
        final String email = userCreateRequest.getEmail();
        final String password = userCreateRequest.getPassword();
        final User newUser = userAuthService.createUser(email, password);

        return ResponseEntity.ok(UserCreateResponse.builder()
                .success(true)
                .user(newUser)
                .message("User registered successfully.")
                .build());
    }

    @PostMapping("/user/authenticate")
    @Operation(summary = "Provides user authentication details to the authentication service",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful",
                            content = @Content(schema = @Schema(implementation = UserAuthenticationResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Authentication failed",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<UserAuthenticationResponse> authenticateUser(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        final String email = userAuthenticationRequest.getEmail();
        final User user = userAuthService.authenticateUser(email);
        return ResponseEntity.ok(UserAuthenticationResponse.builder()
                .authenticated(true)
                .user(user)
                .message("Authentication successful.")
                .build());
    }

    @PatchMapping("/user/change-password")
    @Operation(summary = "Allows authenticated users to change their password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password changed successfully",
                            content = @Content(schema = @Schema(implementation = ChangePasswordResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid password change request",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(ChangePasswordResponse.builder()
                .success(userAuthService.changeCurrentUserPassword(changePasswordRequest.getNewPassword()))
                .success(true)
                .message("Password changed successfully.")
                .build());
    }

    @PostMapping("/user/verify-email/confirm")
    @Operation(summary = "Confirms email verification using a token or code provided in the email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email verified successfully",
                            content = @Content(schema = @Schema(implementation = EmailVerificationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired token",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<EmailVerificationResponse> confirmEmailVerification(
            @RequestBody EmailVerificationConfirmationRequest emailVerificationConfirmationRequest) {
        // Placeholder for actual email verification logic
        return ResponseEntity.ok(EmailVerificationResponse.builder()
                .verified(true)
                .message("Email verified successfully.")
                .build());
    }
}

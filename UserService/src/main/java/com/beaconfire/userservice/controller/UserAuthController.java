package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.UserAuthRequest.ChangePasswordRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.EmailVerificationConfirmationRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserAuthenticationRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserRegistrationRequest;
import com.beaconfire.userservice.dto.UserAuthResponse.AuthenticationResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.ChangePasswordResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.EmailVerificationResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.UserRegistrationResponse;

import com.beaconfire.userservice.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/user/register")
    @Operation(summary = "Registers a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully registered the user",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid user registration details")
            })
    public ResponseEntity<UserRegistrationResponse> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        final String email = userRegistrationRequest.getEmail();
        final String password = userRegistrationRequest.getPassword();
        User newUser = userAuthService.registerUser(email, password);

        return ResponseEntity.ok(UserRegistrationResponse.builder()
                .success(true)
                .userId(newUser.getId())
                .message("User registered successfully.")
                .build());
    }

    @PostMapping("/user/authenticate")
    @Operation(summary = "Provides user authentication details to the authentication service",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful"),
                    @ApiResponse(responseCode = "401", description = "Authentication failed")
            })
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
        //place holder for actual authentication logic
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .authenticated(true)
                .message("User authenticated successfully.")
                .build());
    }

    @PostMapping("/user/change-password")
    @Operation(summary = "Allows authenticated users to change their password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password changed successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid password change request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        //place holder for actual password change logic
        return ResponseEntity.ok(ChangePasswordResponse.builder()
                .success(true)
                .message("Password changed successfully.")
                .build());
    }

    @PostMapping("/user/verify-email/confirm")
    @Operation(summary = "Confirms email verification using a token or code provided in the email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email verified successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired token")
            })
    public ResponseEntity<EmailVerificationResponse> confirmEmailVerification(@RequestBody EmailVerificationConfirmationRequest emailVerificationConfirmationRequest) {
        //place holder for actual email verification logic
        return ResponseEntity.ok(EmailVerificationResponse.builder()
                .verified(true)
                .message("Email verified successfully.")
                .build());
    }
}

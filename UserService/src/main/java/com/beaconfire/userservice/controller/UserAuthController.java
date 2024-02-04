package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.ChangePasswordRequest;
import com.beaconfire.userservice.dto.EmailVerificationConfirmationRequest;
import com.beaconfire.userservice.dto.UserAuthenticationRequest;
import com.beaconfire.userservice.dto.UserRegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAuthController {

    // ResponseEntity<String> added for testing purposes, will be removed later
    @PostMapping("/user/register")
    @Operation(summary = "Registers a new user in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully registered the user",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid user registration details")
            })
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        String username = userRegistrationRequest.getUsername();
        String email = userRegistrationRequest.getEmail();
        String password = userRegistrationRequest.getPassword();
        return ResponseEntity.ok("User " + username + "\n with email " + email + "\nand password " + password + "\nhas been registered");
    }

    @PostMapping("/user/authenticate")
    @Operation(summary = "Provides user authentication details to the authentication service",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Authentication successful"),
                    @ApiResponse(responseCode = "401", description = "Authentication failed")
            })
    public void authenticateUser(@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
    }

    @PostMapping("/user/change-password")
    @Operation(summary = "Allows authenticated users to change their password",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Password changed successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid password change request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized")
            })
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    }

    @PostMapping("/user/verify-email/confirm")
    @Operation(summary = "Confirms email verification using a token or code provided in the email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email verified successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid or expired token")
            })
    public void confirmEmailVerification(@RequestBody EmailVerificationConfirmationRequest emailVerificationConfirmationRequest) {
    }
}

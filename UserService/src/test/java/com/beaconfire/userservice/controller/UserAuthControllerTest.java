package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.UserAuthRequest.ChangePasswordRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserAuthenticationRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserCreateRequest;
import com.beaconfire.userservice.dto.UserAuthResponse.ChangePasswordResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.UserAuthenticationResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.UserCreateResponse;
import com.beaconfire.userservice.service.UserAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserAuthControllerTest {

    @Mock
    private UserAuthService userAuthService;

    @InjectMocks
    private UserAuthController userAuthController;

    @BeforeEach
    void setUp() {
        // Common setup if necessary
    }

    @Test
    void createUserSuccess() {
        // Arrange
        UserCreateRequest request = new UserCreateRequest("user@example.com", "password");
        User mockUser = new User(); // Assuming User class exists and has necessary fields
        when(userAuthService.createUser(anyString(), anyString())).thenReturn(mockUser);

        // Act
        ResponseEntity<UserCreateResponse> response = userAuthController.createUser(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User registered successfully.", response.getBody().getMessage());
    }

    @Test
    void authenticateUserSuccess() {
        // Arrange
        UserAuthenticationRequest request = new UserAuthenticationRequest("user@example.com");
        User mockUser = new User(); // Assuming User class exists
        when(userAuthService.authenticateUser(anyString())).thenReturn(mockUser);

        // Act
        ResponseEntity<UserAuthenticationResponse> response = userAuthController.authenticateUser(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isAuthenticated());
        assertEquals("Authentication successful.", response.getBody().getMessage());
    }

    @Test
    void changePasswordSuccess() {
        // Arrange
        // Assuming there's a way to pass old and new passwords, and a service method to handle it


        // Act
        // Simulate calling the method with necessary arguments if they were part of the request
        ResponseEntity<ChangePasswordResponse> response = userAuthController.changePassword(ChangePasswordRequest.builder()
                .password("oldPassword")
                .build());

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("Password changed successfully.", response.getBody().getMessage());
    }


}

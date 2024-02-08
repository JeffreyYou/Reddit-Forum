package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.*;
import com.beaconfire.userservice.dto.UserAuthResponse.UserListResponse;
import com.beaconfire.userservice.dto.UserMgmtRequest.UserTypeUpdateRequest;
import com.beaconfire.userservice.dto.UserMgmtResponse.UserGeneralResponse;
import com.beaconfire.userservice.exception.UserNotFoundException;
import com.beaconfire.userservice.service.UserMgmtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserMgmtControllerTest {
    final User usera = User.builder().id(1L)
            .firstName("usera")
            .lastName("aa")
            .email("123@gm.com")
            .dateJoined(new Timestamp(System.currentTimeMillis()))
            .profileImageURL("url")
            .verified(true)
            .password("password")
            .type("admin")
            .active(true)
            .build();
    final User userb = User.builder().id(1L)
            .firstName("usera")
            .lastName("aa")
            .email("123@gm.com")
            .dateJoined(new Timestamp(System.currentTimeMillis()))
            .profileImageURL("url")
            .verified(true)
            .password("password")
            .type("admin")
            .active(true)
            .build();
    @Mock
    private UserMgmtService userMgmtService;

    @InjectMocks
    private UserMgmtController userMgmtController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void getAllUsersSuccess() {
        // Arrange
        when(userMgmtService.getAllUsers()).thenReturn(asList(usera, userb));

        // Act
        ResponseEntity<UserListResponse> response = userMgmtController.getAllUsers();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getUserList());
        assertEquals("List of users retrieved successfully.", response.getBody().getMessage());

        // Verify
        verify(userMgmtService, times(1)).getAllUsers();
    }

    @Test
    void updateUserRolesSuccess() {
        // Arrange
        UserTypeUpdateRequest request = new UserTypeUpdateRequest();
        request.setType("admin");
        Long userId = 1L; // Example user ID

        // Act
        ResponseEntity<UserGeneralResponse> response = userMgmtController.updateUserRoles(userId, request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User roles updated successfully.", response.getBody().getMessage());

        // Verify
        verify(userMgmtService, times(1)).updateUserType(eq(userId), anyString());
    }

    @Test
    void deactivateUserSuccess() {
        // Arrange
        Long userId = 1L; // Example user ID

        // Act
        ResponseEntity<UserGeneralResponse> response = userMgmtController.deactivateUser(userId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User account deactivated successfully.", response.getBody().getMessage());

        // Verify
        verify(userMgmtService, times(1)).deactivateUser(eq(userId));
    }

    @Test
    void listBannedUsersSuccess() {
        // Arrange
        when(userMgmtService.getAllBannedUsers()).thenReturn(asList(usera, userb));

        // Act
        ResponseEntity<UserListResponse> response = userMgmtController.listBannedUsers();

        // Assert
        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().getUserList());
        assertEquals("List of banned users retrieved successfully.", response.getBody().getMessage());

        // Verify
        verify(userMgmtService, times(1)).getAllBannedUsers();
    }

    @Test
    void deactivateUserNotFound() {
        // Arrange
        Long userId = 1L; // Example user ID
        doThrow(new UserNotFoundException("User not found")).when(userMgmtService).deactivateUser(userId);

        // Act & Assert
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userMgmtController.deactivateUser(userId);
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void activateUserSuccess() {
        // Arrange
        Long userId = 1L; // Example user ID

        // Act
        ResponseEntity<UserGeneralResponse> response = userMgmtController.activateUser(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User account reactivated successfully.", response.getBody().getMessage());

        // Verify
        verify(userMgmtService, times(1)).activateUser(userId);
    }

    @Test
    void listActiveUsersSuccess() {
        // Arrange
        when(userMgmtService.getAllActiveUsers()).thenReturn(asList(usera, userb));

        // Act
        ResponseEntity<UserListResponse> response = userMgmtController.listActiveUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(asList(usera,userb).size(), response.getBody().getUserList().size());
        assertEquals("List of active users retrieved successfully.", response.getBody().getMessage());

        // Verify
        verify(userMgmtService, times(1)).getAllActiveUsers();
    }

}

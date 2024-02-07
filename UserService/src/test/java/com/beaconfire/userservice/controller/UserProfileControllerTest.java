package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.UserMgmtResponse.UserGeneralResponse;
import com.beaconfire.userservice.dto.UserProfileRequest.SetUserFieldRequest;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserActiveResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserProfileFieldResponse;
import com.beaconfire.userservice.service.UserAuthService;
import com.beaconfire.userservice.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserVerifiedResponse;
import com.beaconfire.userservice.dto.UserProfileResponse.GetUserProfileResponse;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {
    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserAuthService userAuthService;

    @InjectMocks
    private UserProfileController userProfileController;

    private User mockUser;
    private final Long userId = 1L;
    @BeforeEach
    void setUp() {
        // Mocking the getCurrentUserId to return a fixed user ID for all tests
        mockUser = new User();
        when(userAuthService.getCurrentUserId()).thenReturn(1L);
    }

    @Test
    void getUserProfileSuccess() {
        // Arrange
        User user = new User(); // Prepare a mock User with required fields
        when(userProfileService.findUserById(1L)).thenReturn(user);

        // Act
        ResponseEntity<GetUserProfileResponse> response = userProfileController.getUserProfile();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull("Body should not be null",response.getBody());
        assertEquals(user, response.getBody().getUser());
        assertEquals("Return user profile successfully.", response.getBody().getMessage());
    }

    @Test
    void updateUserProfileImageURLSuccess() {
        // Arrange
        SetUserFieldRequest request = SetUserFieldRequest.builder()
                .fieldName("profileImageUrl")
                .fieldValue("newImageUrl")
                .build();

        // Act
        ResponseEntity<UserGeneralResponse> response = userProfileController.updateUserProfileImageURL(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User profile image URL updated successfully.", response.getBody().getMessage());
    }
    @Test
    void isUserVerifiedSuccess() {
        // Arrange
        User user = new User();
        user.setVerified(true);
        when(userProfileService.findUserById(1L)).thenReturn(user);

        // Act
        ResponseEntity<GetUserVerifiedResponse> response = userProfileController.isUserVerified();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isVerified());
        assertEquals("Return user verified successfully.", response.getBody().getMessage());
    }

    @Test
    void getUserStatusSuccess() {
        // Arrange
        User user = new User(); // Assuming a User class exists with appropriate fields
        user.setActive(true);
        when(userProfileService.findUserById(1L)).thenReturn(user);

        // Act
        ResponseEntity<GetUserActiveResponse> response = userProfileController.getUserStatus();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isActivated());
        assertEquals("Return user status successfully.", response.getBody().getMessage());
    }

    @Test
    void getUserTypeSuccess() {
        // Arrange
        User user = new User(); // Assuming a User class exists
        user.setType("Admin");
        when(userProfileService.findUserById(1L)).thenReturn(user);

        // Act
        ResponseEntity<GetUserProfileFieldResponse> response = userProfileController.getUserType();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("type", response.getBody().getFieldName());
        assertEquals("Admin", response.getBody().getValue());
        assertEquals("Return user type successfully.", response.getBody().getMessage());
    }

    @Test
    void getUserFirstNameSuccess() {
        // Arrange
        String firstName = "John";
        mockUser.setFirstName(firstName);
        when(userProfileService.findUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<GetUserProfileFieldResponse> response = userProfileController.getUserFirstName();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(firstName, response.getBody().getValue());
        assertEquals("Return user first name successfully.", response.getBody().getMessage());
    }

    @Test
    void updateUserFirstNameSuccess() {
        // Arrange
        SetUserFieldRequest request = SetUserFieldRequest.builder()
                .fieldName("firstName")
                .fieldValue("NewFirstName")
                .build();

        // Act
        ResponseEntity<UserGeneralResponse> response = userProfileController.updateUserFirstName(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User first name updated successfully.", response.getBody().getMessage());
    }

    @Test
    void getUserProfileImageURLSuccess() {
        // Arrange
        String profileImageUrl = "http://example.com/image.jpg";
        mockUser.setProfileImageURL(profileImageUrl);
        when(userProfileService.findUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<GetUserProfileFieldResponse> response = userProfileController.getUserProfileImageURL();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profileImageUrl, response.getBody().getValue());
        assertEquals("Return user profile image URL successfully.", response.getBody().getMessage());
    }

    @Test
    void getUserEmailSuccess() {
        // Arrange
        String userEmail = "user@example.com";
        mockUser.setEmail(userEmail);
        when(userProfileService.findUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<GetUserProfileFieldResponse> response = userProfileController.getUserEmail();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userEmail, response.getBody().getValue());
        assertEquals("Return user email successfully.", response.getBody().getMessage());
    }

    @Test
    void getUserLastNameSuccess() {
        // Arrange
        String lastName = "Doe";
        mockUser.setLastName(lastName);
        when(userProfileService.findUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<GetUserProfileFieldResponse> response = userProfileController.getUserLastName();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lastName, response.getBody().getValue());
        assertEquals("Return user last name successfully.", response.getBody().getMessage());
    }

    @Test
    void updateUserLastNameSuccess() {
        // Arrange
        SetUserFieldRequest request = SetUserFieldRequest.builder()
                .fieldName("lastName")
                .fieldValue("NewLastName")
                .build();

        // Act
        ResponseEntity<UserGeneralResponse> response = userProfileController.updateUserLastName(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isSuccess());
        assertEquals("User last name updated successfully.", response.getBody().getMessage());
    }


    @Test
    void getUserDateJoinedSuccess() {
        // Arrange
        LocalDateTime dateJoined = LocalDateTime.now().minusYears(1);
        mockUser.setDateJoined(Timestamp.valueOf(dateJoined));
        when(userProfileService.findUserById(userId)).thenReturn(mockUser);

        // Act
        ResponseEntity<GetUserProfileFieldResponse> response = userProfileController.getUserDateJoined();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Timestamp.valueOf(dateJoined).toString(), response.getBody().getValue());
        assertEquals("Return user date joined successfully.", response.getBody().getMessage());
    }

}

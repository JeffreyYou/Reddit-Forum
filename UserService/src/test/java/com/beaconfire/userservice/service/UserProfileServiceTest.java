package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserFieldNotFoundException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileService userProfileService;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize a user object for reuse in test methods
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setProfileImageURL("http://example.com/image.jpg");
    }

    @Test
    void findUserById_UserExists_ReturnsUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userProfileService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    void findUserById_UserNotFound_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userProfileService.findUserById(1L));
    }

    @Test
    void setUserField_UpdateFirstName_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userProfileService.setUserField(1L, "firstName", "Jane");

        assertEquals("Jane", user.getFirstName());
        verify(userRepository).save(user);
    }

    @Test
    void setUserField_UpdateLastName_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userProfileService.setUserField(1L, "lastName", "Doe");

        assertEquals("Doe", user.getLastName());
        verify(userRepository).save(user);
    }

    @Test
    void setUserField_UpdateProfileImageURL_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userProfileService.setUserField(1L, "profileImageURL", "http://example.com/newimage.jpg");

        assertEquals("http://example.com/newimage.jpg", user.getProfileImageURL());
        verify(userRepository).save(user);
    }

    @Test
    void setUserField_InvalidFieldName_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(UserFieldNotFoundException.class, () -> userProfileService.setUserField(1L, "invalidField", "value"));
    }

    @Test
    void setUserField_UserNotFound_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userProfileService.setUserField(1L, "firstName", "Jane"));
    }
}

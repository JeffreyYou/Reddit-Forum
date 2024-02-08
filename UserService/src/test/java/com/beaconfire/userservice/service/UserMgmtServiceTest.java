package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMgmtServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserMgmtService userMgmtService;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize a user object for reuse
        user = new User();
        user.setId(1L);
        user.setActive(true);
        user.setType("USER");
    }

    @Test
    void updateUserType_UserExists_ReturnsUpdatedType() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        String updatedType = "ADMIN";

        String result = userMgmtService.updateUserType(1L, updatedType);

        assertEquals(updatedType, result);
        assertEquals(updatedType, user.getType());
        verify(userRepository).save(user);
    }

    @Test
    void updateUserType_UserNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userMgmtService.updateUserType(1L, "ADMIN"));
    }

    @Test
    void deactivateUser_UserExists_ReturnsFalse() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Boolean isActive = userMgmtService.deactivateUser(1L);

        assertFalse(isActive);
        assertFalse(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void deactivateUser_UserNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userMgmtService.deactivateUser(1L));
    }

    @Test
    void activateUser_UserExists_ReturnsTrue() {
        user.setActive(false); // Ensure user is initially inactive
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Boolean isActive = userMgmtService.activateUser(1L);

        assertTrue(isActive);
        assertTrue(user.isActive());
        verify(userRepository).save(user);
    }

    @Test
    void activateUser_UserNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userMgmtService.activateUser(1L));
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        List<User> users = Arrays.asList(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userMgmtService.getAllUsers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void getAllBannedUsers_ReturnsBannedUsers() {
        User bannedUser = new User(); // Initialize with appropriate values
        bannedUser.setActive(false);
        List<User> bannedUsers = Collections.singletonList(bannedUser);
        when(userRepository.findByActive(false)).thenReturn(bannedUsers);

        List<User> result = userMgmtService.getAllBannedUsers();

        assertFalse(result.isEmpty());
        assertFalse(result.get(0).isActive());
        assertEquals(1, result.size());
        verify(userRepository).findByActive(false);
    }

    @Test
    void getAllActiveUsers_ReturnsActiveUsers() {
        User activeUser = new User(); // Initialize with appropriate values
        activeUser.setActive(true);
        List<User> activeUsers = Collections.singletonList(activeUser);
        when(userRepository.findByActive(true)).thenReturn(activeUsers);

        List<User> result = userMgmtService.getAllActiveUsers();

        assertFalse(result.isEmpty());
        assertTrue(result.get(0).isActive());
        assertEquals(1, result.size());
        verify(userRepository).findByActive(true);
    }

}

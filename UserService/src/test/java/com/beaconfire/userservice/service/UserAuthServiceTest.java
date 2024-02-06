package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserAuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAuthService userAuthService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .firstName("New")
                .lastName("User")
                .email("test@example.com")
                .password("password")
                .active(true)
                .type("USER_ROLE")
                .profileImageURL("")
                .dateJoined(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    void whenRegisterUserWithExistingEmail_thenThrowException() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        assertThrows(UserAlreadyExistsException.class, () -> userAuthService.createUser(testUser.getEmail(), testUser.getPassword()));
    }

    @Test
    void whenRegisterUserWithNewEmail_thenSaveUser() {
        when(userRepository.findByEmail(testUser.getEmail()))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        assertDoesNotThrow(() -> userAuthService.createUser(testUser.getEmail(), testUser.getPassword()));
    }

    @Test
    void whenAuthenticateUserWithValidCredentials_thenReturnTrue() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        verify(userAuthService.authenticateUser(testUser.getEmail()));
    }

    @Test
    void whenAuthenticateUserWithInvalidUserId_thenThrowException() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userAuthService.authenticateUser(testUser.getEmail()));
    }

    @Test
    void whenChangePasswordForExistingUser_thenUpdatePassword() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        userAuthService.changeCurrentUserPassword("newPassword");
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void whenSetVerifiedForExistingUser_thenUserVerified() {
        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        assertTrue(userAuthService.setVerified(testUser.getId()));
        verify(userRepository, times(1)).save(testUser);
    }
}


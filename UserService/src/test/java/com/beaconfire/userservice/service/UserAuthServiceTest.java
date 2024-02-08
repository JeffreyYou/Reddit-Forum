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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserAuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAuthService userAuthService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void changeCurrentUserPassword_Success() {
        Long userId = 1L;
        String newPassword = "newPassword";
        User user = new User();
        user.setId(userId);
        user.setPassword("oldPassword");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userId.toString(), null);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        userAuthService.changeCurrentUserPassword(newPassword);

        assertEquals(newPassword, user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void setVerified_UserNotFound_ThrowsException() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userAuthService.setVerified(userId));

        // Verify userRepository.save() is never called since no user was found
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void setVerified_UserFound_SetsVerifiedTrue() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setVerified(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        boolean result = userAuthService.setVerified(userId);

        // Assert
        assertTrue(result);
        assertTrue(user.isVerified());
        verify(userRepository).save(user); // Verify user is saved with verified status
    }

    @Test
    void changeCurrentUserPassword_UserNotFound_ThrowsException() {
        // Mock SecurityContext and Authentication to return a specific userId
        mockAuthenticatedUser(1L);

        // Mock userRepository to return an empty Optional when findById is called
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute the method and verify that UserNotFoundException is thrown
        assertThrows(UserNotFoundException.class, () -> userAuthService.changeCurrentUserPassword("newPassword"));

        // Optionally, verify that save is never called since the user was not found
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getCurrentUserId_AuthenticationIsNull() {
        // Set SecurityContext with non-null but not authenticated Authentication
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        UserAuthService service = new UserAuthService(null); // Assuming userRepository is not used here
        assertNull(service.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_AuthenticationIsNotAuthenticated() {
        // Set SecurityContext with non-null but not authenticated Authentication
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.setContext(securityContext);

        UserAuthService service = new UserAuthService(null); // Assuming userRepository is not used here
        assertNull(service.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_AuthenticatedWithParsablePrincipal() {
        // Set SecurityContext with authenticated Authentication and a parsable principal
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("123"); // Principal that can be parsed to Long
        SecurityContextHolder.setContext(securityContext);

        UserAuthService service = new UserAuthService(null); // Assuming userRepository is not used here
        assertEquals(123L, service.getCurrentUserId());
    }

    @Test
    void getCurrentUserId_AuthenticatedWithUnparsablePrincipal() {
        // Set SecurityContext with authenticated Authentication and a non-parsable principal
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("notALong");
        SecurityContextHolder.setContext(securityContext);

        UserAuthService service = new UserAuthService(null); // Assuming userRepository is not used here

        assertThrows(NumberFormatException.class, service::getCurrentUserId);
    }

    @Test
    void createUser_Success() {
        String email = "test@example.com";
        String password = "password";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        User user = userAuthService.createUser(email, password);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_Failure_UserAlreadyExists() {
        String email = "existing@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userAuthService.createUser(email, "password");
        });
    }

    @Test
    void authenticateUser_Success() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = userAuthService.authenticateUser(email);

        assertEquals(email, result.getEmail());
    }

    @Test
    void authenticateUser_Failure_UserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userAuthService.authenticateUser("nonexistent@example.com");
        });
    }

    @Test
    void setVerified_Success() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setVerified(false);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        boolean result = userAuthService.setVerified(userId);

        assertTrue(result);
        assertTrue(user.isVerified());
        verify(userRepository).save(user);
    }

    private void mockAuthenticatedUser(Long userId) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(authentication.getPrincipal()).thenReturn(userId.toString());
        when(authentication.isAuthenticated()).thenReturn(true);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

}


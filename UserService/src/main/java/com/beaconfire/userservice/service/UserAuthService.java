package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserAuthService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> role.equals(authority.getAuthority()));
        }
        return false;
    }

    public static String getCurrentEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getPrincipal().toString();

        }
        return null;
    }

    public long getCurrentUserId() {
        Long userId = getCurrentUserId();
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with userId: " + userId)).getId();
    }

    public User registerUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        userRepository.save( User.builder()
                .firstName("New")
                .lastName("User")
                .active(true)
                .type("USER_ROLE")
                .profileImageURL("")
                .email(email)
                .password(password)
                .dateJoined(new Timestamp(System.currentTimeMillis()))
                .build());
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }

    public boolean authenticateUser(Long userId, String password) {
        final User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        return user.getPassword().equals(password);
    }

    public boolean changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        user.setPassword(newPassword);
        userRepository.save(user);
        return false;
    }

    public boolean setVerified(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

}

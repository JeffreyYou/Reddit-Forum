package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.InvalidUserPasswordException;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import com.beaconfire.userservice.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getPrincipal());
            return Long.parseLong(authentication.getPrincipal().toString());
        }
        return null;
    }

    public User getCurrentUser() {
        Long userId = getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("No authenticated user found");
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    /******** integrate with emailService (newly added) ***********/
    public User createUser(String email, String password, String firstname, String lastname, String emailToken) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        userRepository.save( User.builder()
                .firstName(firstname)
                .lastName(lastname)
                .active(true)
                .type("user")
                .profileImageURL("")
                .email(email)
                .password(password)
                .dateJoined(new Timestamp(System.currentTimeMillis()))
                .emailToken(emailToken)
                .emailTokenExpiredTime(new Timestamp(System.currentTimeMillis() + EmailService.getEmailValidPeriod()))
                .verified(false)
                .build());
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }

    /******** old version *********/
    public User createUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        userRepository.save( User.builder()
                .firstName("New")
                .lastName("User")
                .active(true)
                .type("user")
                .profileImageURL("")
                .email(email)
                .password(password)
                .dateJoined(new Timestamp(System.currentTimeMillis()))
                .build());
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }

    public User authenticateUser(String useremail) {
        return userRepository.findByEmail(useremail).orElseThrow(() -> new UserNotFoundException("User with email " + useremail + " not found."));
    }

    public boolean changeCurrentUserPassword(String newPassword) {
        User user = getCurrentUser();
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

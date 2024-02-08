package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import com.beaconfire.userservice.exception.UserNotFoundException;
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

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getPrincipal());
            return Long.parseLong(authentication.getPrincipal().toString());
        }
        return null;
    }

    public User createUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
        final User user = User.builder()
                .firstName("")
                .lastName("")
                .email(email)
                .password(password)
                .active(true)
                .verified(false)
                .type("user")
                .dateJoined(new Timestamp(System.currentTimeMillis()))
                .profileImageURL("")
                .build();
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User authenticateUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }

    @Transactional
    public void changeCurrentUserPassword(String newPassword) {
        final Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Transactional
    public boolean setVerified(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

}

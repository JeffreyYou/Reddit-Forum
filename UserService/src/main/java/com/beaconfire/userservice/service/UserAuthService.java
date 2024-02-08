package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserAuthService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public UserAuthService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println(authentication.getPrincipal());
            return Long.parseLong(authentication.getPrincipal().toString());
        }
        return null;
    }

    /******** integrate with emailService (newly added) ***********/
    public User createUser(String email, String password, String firstname, String lastname) {
        // check if email exists immediately
        emailExistsCheck(email);
        final String emailToken = emailService.sendEmail(email, firstname);

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

    public User authenticateUser(String useremail) {
        return userRepository.findByEmail(useremail).orElseThrow(() -> new UserNotFoundException("User with email " + useremail + " not found."));
    }

    public void changeCurrentUserPassword(String newPassword) {
        final Long userId = getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public boolean setVerified(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found."));
        user.setVerified(true);
        userRepository.save(user);
        return true;
    }

    public void emailExistsCheck(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }
    }
}

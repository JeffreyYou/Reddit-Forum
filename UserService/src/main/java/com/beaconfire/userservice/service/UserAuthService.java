package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User registerUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists.");
        }

        User newUser = User.builder()
                .firstName("New")
                .lastName("User")
                .active(true)
                .type("USER_ROLE")
                .profileImageURL("")
                .email(email)
                .password(passwordEncoder.encode(password))
                .dateJoined(new Timestamp(System.currentTimeMillis()))
                .build();

        userRepository.save(newUser);

        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found."));
    }
}

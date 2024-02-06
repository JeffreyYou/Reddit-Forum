package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.dto.UserProfileRequest.UpdateUserProfileRequest;
import com.beaconfire.userservice.dto.UserProfileResponse.UserProfileResponse;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserRepository userRepository;

    @Autowired
    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserStatus(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(user -> user.isActive() ? "Active" : "Banned")
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public String getUserType(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getType)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public String getUserFirstNameById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getFirstName)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public String getUserLastNameById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getLastName)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public String getUserEmailById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public Timestamp getUserDateJoinedById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getDateJoined)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public String getUserProfileImageURLById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::getProfileImageURL)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    public Boolean getUserVerifiedById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.map(User::isVerified)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Transactional
    public boolean updateUserProfileById(Long userId, UpdateUserProfileRequest updateUserProfileRequest) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Update the user profile fields
            user.setFirstName(updateUserProfileRequest.getFirstName());
            user.setLastName(updateUserProfileRequest.getLastName());
            user.setEmail(updateUserProfileRequest.getEmail());
            user.setProfileImageURL(updateUserProfileRequest.getProfileImageURL());
            userRepository.save(user);
        }
        throw new UserNotFoundException("User not found with ID: " + userId);
    }

    public UserProfileResponse getUserProfileById(Long userId) {
        final Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserProfileResponse.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .type(user.getType())
                    .verified(user.isVerified())
                    .dateJoined(user.getDateJoined())
                    .profileImageURL(user.getProfileImageURL())
                    .active(user.isActive())
                    .build();
        }
        throw new UserNotFoundException("User not found with ID: " + userId);
    }
}

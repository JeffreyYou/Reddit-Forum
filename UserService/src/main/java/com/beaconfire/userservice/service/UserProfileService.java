package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.exception.UserFieldNotFoundException;
import com.beaconfire.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserRepository userRepository;

    @Autowired
    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Transactional
    public void setUserField(Long userId, String fieldName, String fieldValue)  {
        User user = findUserById(userId);
        switch (fieldName) {
            case "firstName":
                user.setFirstName(fieldValue);
                break;
            case "lastName":
                user.setLastName(fieldValue);
                break;
            case "profileImageURL":
                user.setProfileImageURL(fieldValue);
                break;
            default:
                throw new UserFieldNotFoundException("Invalid attribute name: " + fieldName);
        }
        userRepository.save(user);
    }
}

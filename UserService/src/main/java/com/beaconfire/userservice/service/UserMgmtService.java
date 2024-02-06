package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMgmtService {

    private final UserRepository userRepository;

    @Autowired
    public UserMgmtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String updateUserType(Long userId, String userType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setType(userType);
        userRepository.save(user);
        return user.getType();
    }

    public Boolean deactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setActive(false);
        userRepository.save(user);
        return user.isActive();
    }

    public Boolean activateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        user.setActive(true);
        userRepository.save(user);
        return user.isActive();
    }

    public List<User> getAllUsers() {
        //place holder for actual logic
        return userRepository.findAll();
    }

    public List<User> getAllBannedUsers() {
        return userRepository.findByActive(false);
    }

    public java.util.List<User> getAllActiveUsers() {
        return userRepository.findByActive(true);
    }

}

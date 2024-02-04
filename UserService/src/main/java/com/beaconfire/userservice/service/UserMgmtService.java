package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserMgmtService {

    private final UserRepository userRepository;

    @Autowired
    public UserMgmtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String updateUserType(Long userId, String userType) {
        //place holder for actual logic
        return "ROLE_PLACEHOLDER";
    }

    public Boolean deactivateUser(Long userId) {
        //place holder for actual logic
        return true;
    }

    public Boolean activateUser(Long userId) {
        //place holder for actual logic
        return true;
    }

    public Page<User> getAllUsers(Pageable pageable) {
        //place holder for actual logic
        return userRepository.findAll(pageable);
    }

}

package com.beaconfire.authservice.service;

import com.beaconfire.userservice.dto.UserAuthRequest.UserCreateRequest;

import com.beaconfire.userservice.dto.UserAuthResponse.UserCreateResponse;
import com.beaconfire.userservice.exception.UserAlreadyExistsException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;

@Service
public class AuthRegisterService {

    @Autowired
    private UserServiceClient userServiceClient;

    public UserCreateResponse registerUser(String email, String password) {
        UserCreateResponse userCreateResponse;
        try {
            userCreateResponse = userServiceClient.register(
                    UserCreateRequest.builder().email(email).password(password).build()
            );
        }
        catch (UserAlreadyExistsException ex) {
            throw new UserAlreadyExistsException("User already exists");
        }
        return userCreateResponse;
    }
}

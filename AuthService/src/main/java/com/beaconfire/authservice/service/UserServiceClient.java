package com.beaconfire.authservice.service;

import com.beaconfire.authservice.config.FeignClientConfig;
import com.beaconfire.userservice.dto.UserAuthRequest.UserAuthenticationRequest;
import com.beaconfire.userservice.dto.UserAuthRequest.UserCreateRequest;
import com.beaconfire.userservice.dto.UserAuthResponse.UserAuthenticationResponse;
import com.beaconfire.userservice.dto.UserAuthResponse.UserCreateResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface UserServiceClient {
    @PostMapping("/user-service/user/authenticate")
    UserAuthenticationResponse authenticate(@RequestBody UserAuthenticationRequest request);

    @PostMapping("/user-service/user/create")
    UserCreateResponse register(@RequestBody UserCreateRequest request);
}

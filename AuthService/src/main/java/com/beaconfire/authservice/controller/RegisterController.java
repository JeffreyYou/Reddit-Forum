package com.beaconfire.authservice.controller;

import com.beaconfire.authservice.dto.request.AuthRegRequest;
import com.beaconfire.authservice.dto.response.AuthRegResponse;
import com.beaconfire.authservice.security.JwtProvider;
import com.beaconfire.authservice.service.AuthRegisterService;
import com.beaconfire.userservice.dto.UserAuthResponse.UserCreateResponse;
import com.beaconfire.userservice.security.AuthUserDetail;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthRegisterService authRegisterService;

//    @GetMapping()

    @PostMapping("/register")
    public ResponseEntity<AuthRegResponse> registerNewUser(@RequestBody AuthRegRequest authRegRequest) {
        UserCreateResponse userCreateResponse = authRegisterService.registerUser(
                authRegRequest.getEmail(), authRegRequest.getPassword()
        );
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userCreateResponse.getUser().getEmail(), userCreateResponse.getUser().getPassword()
                    )
            );
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(
                    AuthRegResponse.builder().message("Wrong User Credential").build(),
                    HttpStatus.UNAUTHORIZED
            );
        }
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
        String token = jwtProvider.createToken(authUserDetail);
        return new ResponseEntity<>(
                AuthRegResponse.builder().message("User successfully registered").token(token).build(),
                HttpStatus.CREATED
        );
    }
}

package com.beaconfire.authservice.controller;

import com.beaconfire.authservice.dto.LoginResponse;
import com.beaconfire.authservice.dto.RegisterRequest;
import com.beaconfire.authservice.dto.RegisterResponse;
import com.beaconfire.authservice.dto.LoginRequest;
import com.beaconfire.authservice.dto.request.AuthLoginRequest;
import com.beaconfire.authservice.dto.response.AuthLoginResponse;
import com.beaconfire.authservice.service.AuthLoginService;
import com.beaconfire.userservice.dto.UserAuthResponse.UserAuthenticationResponse;
import com.beaconfire.userservice.security.AuthUserDetail;
import com.beaconfire.authservice.security.JwtProvider;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthLoginService authLoginService;

//    @PostMapping("/login")
//    public ResponseEntity<AuthLoginResponse> loginForUser(@RequestBody AuthLoginRequest authLoginRequest) {
//        UserAuthenticationResponse userAuthenticationResponse = authLoginService;
//    }

//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody LoginRequest request) {
//
//        Authentication authentication;
//
//        //Try to authenticate the user using the username and password
//        try {
//            authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//        } catch (AuthenticationException e) {
//            throw new BadCredentialsException("Provided credential is invalid.");
//        }
//
//        //Successfully authenticated user will be stored in the authUserDetail object
//        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object
//
//        //A token wil be created using the username/email/userId and permission
//        String token = jwtProvider.createToken(authUserDetail);
//
//        //Returns the token as a response to the frontend/postman
//        return LoginResponse.builder()
//                .message("Welcome " + authUserDetail.getUsername())
//                .token(token)
//                .build();
//
//    }

}

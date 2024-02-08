package com.beaconfire.authservice.controller;

import com.beaconfire.authservice.dto.LoginResponse;
import com.beaconfire.authservice.dto.RegisterRequest;
import com.beaconfire.authservice.dto.RegisterResponse;
import com.beaconfire.authservice.dto.LoginRequest;
import com.beaconfire.authservice.dto.request.AuthLoginRequest;
import com.beaconfire.authservice.dto.response.AuthLoginResponse;
import com.beaconfire.authservice.dto.response.AuthRegResponse;
import com.beaconfire.authservice.service.AuthLoginService;
import com.beaconfire.userservice.dto.UserAuthResponse.UserAuthenticationResponse;
import com.beaconfire.userservice.security.AuthUserDetail;
import com.beaconfire.authservice.security.JwtProvider;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> loginForUser(@RequestBody AuthLoginRequest authLoginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authLoginRequest.getEmail(), authLoginRequest.getPassword()
                    )
            );
        }
        catch (AuthenticationException ex) {
            return new ResponseEntity<>(
                    AuthLoginResponse.builder().message("Wrong User Credential").build(),
                    HttpStatus.UNAUTHORIZED
            );
        }
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal();
        String token = jwtProvider.createToken(authUserDetail);
        return new ResponseEntity<>(
                AuthLoginResponse.builder().message("User successfully login").token(token).build(),
                HttpStatus.OK
        );
    }
}

package com.beaconfire.authservice.service;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.security.AuthUserDetail;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.beaconfire.userservice.dto.UserAuthResponse.UserAuthenticationResponse;
import com.beaconfire.userservice.dto.UserAuthRequest.UserAuthenticationRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthLoginService implements UserDetailsService {

    @Autowired
    private UserServiceClient userServiceClient;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthenticationResponse response;
        try {
            response = userServiceClient.authenticate(UserAuthenticationRequest.builder()
                    .email(username)
                    .build());
        } catch (FeignException e) {
            throw new UsernameNotFoundException("User not found or error in authentication service", e);
        }

        User user = response.getUser();
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return AuthUserDetail.builder()
                .username(user.getEmail())
                .password(new BCryptPasswordEncoder().encode(user.getPassword())) // if password was not encoded during registration
                .authorities(convertTypeToAuthorities(user.getType()))
                .enabled(true)
                .build();
    }


    private Collection<? extends GrantedAuthority> convertTypeToAuthorities(String type) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Always assign USER_ROLE
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if ("ADMIN".equalsIgnoreCase(type)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("SUPER_ADMIN".equalsIgnoreCase(type)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // SUPER_ADMIN includes ADMIN_ROLE
            authorities.add(new SimpleGrantedAuthority("ROLE_SADMIN"));
        }

        return authorities;
    }
}

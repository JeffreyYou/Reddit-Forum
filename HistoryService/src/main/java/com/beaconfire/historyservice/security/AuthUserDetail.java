package com.beaconfire.historyservice.security;

import com.beaconfire.historyservice.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter // Generates getters for all fields
@RequiredArgsConstructor // Generates a constructor for all final fields
public class AuthUserDetail implements UserDetails {

    private final Long id;
    private final String username; // Email as username
    private final String password;
    private final boolean enabled;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired = true; // For simplicity, assuming always true
    private final boolean accountNonLocked = true;
    private final boolean credentialsNonExpired = true;
    private final boolean verified;

    // Constructor that accepts the User entity
    public AuthUserDetail(User user) {
        this.id = user.getId();
        this.username = user.getEmail(); // Assuming the email is used as the username
        this.password = user.getPassword();
        this.enabled = user.isActive();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getType()));
        this.verified = user.isVerified();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    // Note: No need for custom getters due to @Getter annotation
}

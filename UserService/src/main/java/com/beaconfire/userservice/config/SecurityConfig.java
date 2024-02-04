package com.beaconfire.userservice.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // This method is used to configure the security of the application
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        // Additional HTTP security configurations go here
    }
}
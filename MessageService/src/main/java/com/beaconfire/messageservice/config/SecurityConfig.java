package com.beaconfire.messageservice.config;


import com.beaconfire.messageservice.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtTokenFilter; // Your custom JWT filter

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable() // Disable CSRF protection
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // Use stateless sessions
                .authorizeRequests()
                .antMatchers("/message-service/swagger-ui/**", "/message-service/v3/**", "/message-service/swagger-ui*").permitAll()
                .antMatchers("/message-service/**").permitAll()
                .anyRequest().authenticated() // Require authentication for any other request
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add your custom JWT filter before the Spring Security filter
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define the password encoder
    }
}
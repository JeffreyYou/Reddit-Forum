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
    private JwtFilter jwtTokenFilter; // Ensure this filter is defined and autowired

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();  // Apply CORS if necessary
        http
                .csrf().disable() // Disable CSRF as we use token-based authentication
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be maintained
                .and()
                .authorizeRequests()
//                .antMatchers("/user-service/user/authenticate", "/user-service/user/create").permitAll() // Allow unauthenticated access to these endpoints
                .anyRequest().authenticated() // All other requests must be authenticated
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add your custom JWT filter
    }

    // Optional: Configuring CORS to allow requests from specific origins, methods, etc.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Configure the allowed origins or specify specific domains
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // Allow standard methods
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token")); // Allow headers such as Authorization
        configuration.setExposedHeaders(Arrays.asList("x-auth-token")); // Expose specific headers
        configuration.setAllowCredentials(true); // Allow credentials
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply this configuration to all paths
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

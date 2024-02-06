package com.beaconfire.authservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Assuming you have a method to obtain a JWT token
            String token = "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjIsInBlcm1pc3Npb25zIjpbeyJhdXRob3JpdHkiOiJVU0VSIn1dfQ.O_ojYJWjSga-e4R98nyYrUFvPBXnGJzelaxpN5IiSYQ";
            requestTemplate.header("Authorization", token);
        };
    }
}

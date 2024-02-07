package com.beaconfire.postandreplyservice.config;

import com.beaconfire.postandreplyservice.util.JwtTokenRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public JwtTokenRequestInterceptor jwtTokenRequestInterceptor() {
        return new JwtTokenRequestInterceptor();
    }
}

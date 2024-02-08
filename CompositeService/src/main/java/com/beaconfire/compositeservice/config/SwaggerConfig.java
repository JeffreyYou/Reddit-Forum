package com.beaconfire.compositeservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("Composite-service")
                .packagesToScan("com.beaconfire.compositeservice.controller")
                .pathsToMatch("/**")
                .build();
    }
}

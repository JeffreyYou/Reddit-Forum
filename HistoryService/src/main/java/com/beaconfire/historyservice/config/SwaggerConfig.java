package com.beaconfire.historyservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("History-service")
                .packagesToScan("com.beaconfire.historyservice.controller")
                .pathsToMatch("/**")
                .build();
    }
}

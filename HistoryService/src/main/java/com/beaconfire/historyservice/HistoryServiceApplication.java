package com.beaconfire.historyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class HistoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryServiceApplication.class, args);
    }

}

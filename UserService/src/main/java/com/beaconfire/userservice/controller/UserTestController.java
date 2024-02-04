package com.beaconfire.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserTestController {

    // Test method to check if the user service is working, and to check if the gateway service can access the user service
    // Needed to be deleted when need controller is implemented
    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("gateway test user info");
    }

}
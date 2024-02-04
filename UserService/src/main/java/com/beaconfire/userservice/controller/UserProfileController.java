package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserProfileController {

    @GetMapping("/banned")
    @Operation(summary = "Lists all banned users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of banned users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<User>> listBannedUsers() {
//        List<User> bannedUsers = userService.getAllBannedUsers();
//        return ResponseEntity.ok(bannedUsers);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/active")
    @Operation(summary = "Lists all active (not banned) users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of active users"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<User>> listActiveUsers() {
//        List<User> activeUsers = userService.getAllActiveUsers();
//        return ResponseEntity.ok(activeUsers);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/status/{userId}")
    @Operation(summary = "Retrieves the status (banned or active) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user status"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getUserStatus(@PathVariable Long userId) {
//        String userStatus = userService.getUserStatus(userId);
//        if (userStatus != null) {
//            return ResponseEntity.ok(userStatus);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/role/{userId}")
    @Operation(summary = "Retrieves the type (user, admin, super-admin) of a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user type"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> getUserType(@PathVariable Long userId) {
//        String userRole = userService.getUserType(userId);
//        if (userType != null) {
//            return ResponseEntity.ok(userTypeString);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        // return String for testing purpose
        return ResponseEntity.ok("admin");
    }
}

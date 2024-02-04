package com.beaconfire.userservice.controller;

import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.domain.UserPage;
import com.beaconfire.userservice.dto.ErrorDetails;
import com.beaconfire.userservice.dto.UserMgmtRequest.UserTypeUpdateRequest;
import com.beaconfire.userservice.service.UserMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
public class UserMgmtController {

    private final UserMgmtService userMgmtService;

    @Autowired
    public UserMgmtController(UserMgmtService userService) {
        this.userMgmtService = userService;
    }

    @GetMapping("")
    @Operation(summary = "Retrieves a list of all registered users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users",
                            content = @Content(schema = @Schema(implementation = UserPage.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<Page<User>> getAllUsers(
            @Parameter(description = "Pagination and sorting parameters") Pageable pageable) {
        return ResponseEntity.ok(userMgmtService.getAllUsers(pageable));
    }


    @PatchMapping("/{userId}/roles")
    @Operation(summary = "Updates the roles of a specific user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User roles updated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<String> updateUserRoles(@PathVariable Long userId, @RequestBody UserTypeUpdateRequest request) {
        userMgmtService.updateUserType(userId, request.getType());
        return ResponseEntity.ok("User roles updated successfully");
    }


    @PatchMapping("/{userId}/deactivate")
    @Operation(summary = "Deactivates a user's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User account deactivated successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) {
        userMgmtService.deactivateUser(userId);
        return ResponseEntity.ok("User account deactivated successfully");
    }


    @PatchMapping("/{userId}/activate")
    @Operation(summary = "Reactivates a user's account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User account reactivated successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(schema = @Schema(implementation = ErrorDetails.class)))
            })
    public ResponseEntity<String> activateUser(@PathVariable Long userId) {
        userMgmtService.activateUser(userId);
        return ResponseEntity.ok("User account reactivated successfully");
    }

}

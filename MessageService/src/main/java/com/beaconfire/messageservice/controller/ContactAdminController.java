package com.beaconfire.messageservice.controller;


import com.beaconfire.messageservice.dto.ContactAdminRequest;
import com.beaconfire.messageservice.dto.ContactAdminResponse;
import com.beaconfire.messageservice.service.ContactAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactAdminController {

    private final ContactAdminService contactAdminService;

    @Autowired
    public ContactAdminController(ContactAdminService contactAdminService) {
        this.contactAdminService = contactAdminService;
    }

    @PostMapping("/contactus/submit")
    @Operation(summary = "Submit a message to admins",
            description = "Allows users to submit messages to admins for review. Validates and saves the user's message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message submitted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactAdminResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    //public ResponseEntity<ContactAdminResponse> submitMessage(@RequestBody ContactAdminRequest contactAdminRequest, @AuthenticationPrincipal Long userId) {
    public ResponseEntity<ContactAdminResponse> submitMessage(@RequestBody ContactAdminRequest contactAdminRequest) {
        // dummy userId for testing
        contactAdminService.submitMessage(contactAdminRequest, 1L);
        return ResponseEntity.ok().body(ContactAdminResponse.builder()
                .message("Message submitted successfully").success(true).build());
    }
}

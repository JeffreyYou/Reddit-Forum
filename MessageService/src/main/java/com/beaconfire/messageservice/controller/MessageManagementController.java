package com.beaconfire.messageservice.controller;

import com.beaconfire.messageservice.dto.msgmgmt.MessageListResponse;
import com.beaconfire.messageservice.dto.msgmgmt.MessageResponse;
import com.beaconfire.messageservice.dto.msgmgmt.UpdateMessageStatusResponse;
import com.beaconfire.messageservice.service.MessageManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/messages")
@CrossOrigin(origins = "*")
public class MessageManagementController {

    private final MessageManagementService messageManagementService;

    @Autowired
    public MessageManagementController(MessageManagementService messageManagementService) {
        this.messageManagementService = messageManagementService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @Operation(summary = "Get a list of all messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved a list of messages"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/list")
    public ResponseEntity<MessageListResponse> getAllMessages() {
        List<MessageResponse> responses = messageManagementService.getAllMessages();
        return ResponseEntity.ok(MessageListResponse.builder()
                .messagesList(responses)
                .message("Successfully retrieved a list of contact admin messages.")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @Operation(summary = "Get a message by messageId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the message"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponse> getMessageById(
            @PathVariable @Parameter(description = "Message ID to retrieve", required = true) Long messageId) {
        MessageResponse messageResponse = messageManagementService.getMessageById(messageId);
        if (messageResponse != null) {
            return ResponseEntity.ok(messageResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @Operation(summary = "Open a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully opened message"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/open/{messageId}")
    public ResponseEntity<UpdateMessageStatusResponse> openMessage(
            @PathVariable @Parameter(description = "Message ID to open", required = true) Long messageId) {
        messageManagementService.updateMessageStatus(messageId, "Open");
        return ResponseEntity.ok(UpdateMessageStatusResponse.builder()
                .success(true)
                .message("Successfully opened a message")
                .build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SADMIN')")
    @Operation(summary = "Close a message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully closed message"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/close/{messageId}")
    public ResponseEntity<UpdateMessageStatusResponse> closeMessage(
            @PathVariable @Parameter(description = "Message ID to close", required = true) Long messageId) {
        messageManagementService.updateMessageStatus(messageId, "Closed");
        return ResponseEntity.ok(UpdateMessageStatusResponse.builder()
                .success(true)
                .message("Successfully closed a message")
                .build());
    }
}

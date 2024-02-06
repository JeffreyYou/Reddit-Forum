package com.beaconfire.messageservice.controller;

import com.beaconfire.messageservice.dto.MessageListResponse;
import com.beaconfire.messageservice.dto.MessageResponse;
import com.beaconfire.messageservice.dto.UpdateMessageStatusRequest;
import com.beaconfire.messageservice.dto.UpdateMessageStatusResponse;
import com.beaconfire.messageservice.service.MessageManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/messages")
public class MessageManagementController {

    private final MessageManagementService messageManagementService;

    @Autowired
    public MessageManagementController(MessageManagementService messageManagementService) {
        this.messageManagementService = messageManagementService;
    }

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

    @Operation(summary = "Update message status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated message status"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/update-status")
    public ResponseEntity<UpdateMessageStatusResponse> updateMessageStatus(@RequestBody UpdateMessageStatusRequest request) {
        messageManagementService.updateMessageStatus(request.getMessageId(), request.getNewStatus());
        return ResponseEntity.ok(UpdateMessageStatusResponse.builder()
                .success(true)
                .message("Successfully updated message status")
                .build());
    }
}

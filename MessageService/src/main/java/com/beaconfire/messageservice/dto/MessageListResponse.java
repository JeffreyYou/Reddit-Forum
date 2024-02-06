package com.beaconfire.messageservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MessageListResponse {
    private List<MessageResponse> messagesList;
    private String message;
}


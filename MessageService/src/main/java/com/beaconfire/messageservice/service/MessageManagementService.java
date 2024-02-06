package com.beaconfire.messageservice.service;

import com.beaconfire.messageservice.dao.MessageRepository;
import com.beaconfire.messageservice.domain.Message;
import com.beaconfire.messageservice.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageManagementService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageManagementService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<MessageResponse> getAllMessages() {
        final List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMessageStatus(Long messageId, String newStatus) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("Message not found"));

        message.setStatus(newStatus);
        messageRepository.save(message);
    }

    private MessageResponse convertToResponseDTO(Message message) {
        return MessageResponse.builder()
                .dateCreated(message.getDateCreated())
                .email(message.getEmail())
                .message(message.getMessage())
                .status(message.getStatus())
                .build();
    }
}

package com.beaconfire.messageservice.service;

import com.beaconfire.messageservice.dao.MessageRepository;
import com.beaconfire.messageservice.domain.Message;
import com.beaconfire.messageservice.dto.contactadmin.ContactAdminRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class ContactAdminService {

    private final MessageRepository messageRepository;

    @Autowired
    public ContactAdminService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public void submitMessage(ContactAdminRequest contactAdminRequest, Long userId) {

        Message message = Message.builder()
                .userId(userId)
                .email(contactAdminRequest.getEmail())
                .message(contactAdminRequest.getMessage())
                .dateCreated(Timestamp.from(Instant.now()))
                .status("Open")
                .build();

        messageRepository.save(message);
    }
}

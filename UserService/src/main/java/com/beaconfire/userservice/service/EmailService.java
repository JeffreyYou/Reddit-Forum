package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.domain.message.EmailMessage;
import com.beaconfire.userservice.exception.EmailTokenExpiredException;
import com.beaconfire.userservice.exception.EmailTokenNotFoundException;
import com.beaconfire.userservice.util.EmailMessageUtil;
import com.beaconfire.userservice.util.SerializeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Component
public class EmailService {

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;

    private final String emailExchange = "emailExchange";
    private final String emailRoutingKey = "emailRoutingKey";

    @Autowired
    public EmailService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, UserRepository userRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    public String sendEmail(String email, String firstName) {
        String token = generateToken();
        String tokenizedUrl = generateTokenizedUrl(token);

        EmailMessage newMessage = EmailMessage.builder()
                .to(email)
                .subject(EmailMessageUtil.SUBJECT)
                .body(EmailMessageUtil.generateBody(firstName, tokenizedUrl))
                .build();

        String jsonMessage = SerializeUtil.serialize(newMessage);

        rabbitTemplate.convertAndSend(emailExchange, emailRoutingKey, jsonMessage);

        return token;
    }

    private String generateTokenizedUrl(String token) {
        return EmailMessageUtil.PROTOCOL + "://" + EmailMessageUtil.DOMAIN + "/" + "/user/verify?token=" + token;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean verifyEmail(String token) { // check if token exists and if expired

        User user = userRepository.findByEmailToken(token).orElseThrow(() -> new EmailTokenNotFoundException("User with token " + token + " not found."));

        if (user.getEmailTokenExpiredTime().after(new Timestamp(System.currentTimeMillis()))) {
            user.setVerified(true);
            // update user
            userRepository.save(user);
            return true;
        } else {
            throw new EmailTokenExpiredException("The verification token has expired! Please request once again.");
        }
    }
}
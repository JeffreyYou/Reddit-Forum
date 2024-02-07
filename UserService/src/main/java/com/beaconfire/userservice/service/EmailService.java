package com.beaconfire.userservice.service;

import com.beaconfire.userservice.dao.UserRepository;
import com.beaconfire.userservice.domain.User;
import com.beaconfire.userservice.domain.message.EmailMessage;
import com.beaconfire.userservice.exception.*;
import com.beaconfire.userservice.util.EmailMessageUtil;
import com.beaconfire.userservice.util.SerializeUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.UUID;

@Component
public class EmailService {

    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;

    private static final long emailValidPeriod = 3 * 60 * 60 * 1000; // 3 hrs

    private final String emailExchange = "emailExchange";
    private final String emailRoutingKey = "emailRoutingKey";

    @Autowired
    public EmailService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, UserRepository userRepository, UserAuthService userAuthService) {
        this.rabbitTemplate = rabbitTemplate;
        this.userRepository = userRepository;
    }

    public static long getEmailValidPeriod() {
        return emailValidPeriod;
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

    public boolean updateUserEmail(User user, String newEmail) {
        System.out.println(user.getEmail());
        if (user.getEmail().equals(newEmail) && user.isVerified())
            throw new EmailVerifiedAndUnchangedException("Email is already verified and unchanged.");
        else if (user.getEmail().equals(newEmail))
            throw new EmailVerifiedAndUnchangedException("Email already in use. Please verify your email!");
        // check if the new email already exists and used by another user
        if (userRepository.findByEmail(newEmail).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + newEmail + " already exists.");
        }
        // update email and send verification link
        user.setVerified(false);
        user.setEmail(newEmail);
        String token = sendEmail(newEmail, user.getFirstName());
        user.setEmailToken(token);
        user.setEmailTokenExpiredTime(new Timestamp(System.currentTimeMillis() + emailValidPeriod));
        userRepository.save(user);

        return true;
    }
}
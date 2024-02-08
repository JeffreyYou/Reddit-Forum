package com.beaconfire.emailservice;

import com.beaconfire.emailservice.domain.EmailMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    private final ObjectMapper objectMapper; // Spring Boot automatically configures an ObjectMapper bean

    public EmailService(JavaMailSender javaMailSender, ObjectMapper objectMapper) {
        this.javaMailSender = javaMailSender;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "emailQueue")
    public void sendEmail(Message message) {
        try {
            String json = new String(message.getBody());
            EmailMessage emailMessage = objectMapper.readValue(json, EmailMessage.class);

            sendHtmlEmail(emailMessage.getTo(), emailMessage.getSubject(), emailMessage.getBody());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true indicates this is an HTML email

        javaMailSender.send(mimeMessage);
    }
}
package com.beaconfire.messageservice.exception;

import com.beaconfire.messageservice.dto.ErrorDetails;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Aspect
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Pointcut("within(com.beaconfire.messageservice.controller..*)")
    public void controllerLayerExecution() {
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(MessageNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message("Message not found.")
                .build());
    }
}

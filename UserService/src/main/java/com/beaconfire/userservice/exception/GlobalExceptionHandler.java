package com.beaconfire.userservice.exception;

import com.beaconfire.userservice.dto.ErrorDetails;
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

    @Pointcut("within(com.beaconfire.userservice.controller..*)")
    public void controllerLayerExecution() {
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message("User already exists.")
                .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message("User not found.")
                .build());
    }


    @ExceptionHandler(UserFieldNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserFieldNotFoundException(UserFieldNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message("User field not found")
                .build());
    }

}


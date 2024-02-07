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

    @ExceptionHandler(InvalidUserPasswordException.class)
    public ResponseEntity<ErrorDetails> handleInvalidUserPasswordException(InvalidUserPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message("Authentication failed.")
                .build());
    }

    @ExceptionHandler(EmailTokenNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmailTokenException(EmailTokenNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDetails.builder()
                .details("Token not found")
                .timestamp(LocalDateTime.now())
                .message("Token not found.")
                .build());
    }

    @ExceptionHandler(EmailTokenExpiredException.class)
    public ResponseEntity<ErrorDetails> handleEmailTokenExpiredException(EmailTokenExpiredException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDetails.builder()
                .details("Token is expired")
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .build());
    }

    @ExceptionHandler(EmailVerifiedAndUnchangedException.class)
    public ResponseEntity<ErrorDetails> handleEmailVerifiedAndUnchangedException(EmailVerifiedAndUnchangedException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ErrorDetails.builder()
                .details("email and verified unchanged")
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())

    @ExceptionHandler(UserFieldNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserFieldNotFoundException(UserFieldNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .message("User field not found")
                .build());
    }

}


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
    public void controllerLayerExecution() {}

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorDetails> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), "Specific details if any");
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}


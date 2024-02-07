package com.beaconfire.postandreplyservice.aop;

import com.beaconfire.postandreplyservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {PostNotFoundException.class})
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NonOwnerPostException.class})
    public ResponseEntity<String> handleNonOwnerPostException(NonOwnerPostException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NonOwnerReplyException.class})
    public ResponseEntity<String> handleNonOwnerReplyException(NonOwnerReplyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {StateChangeException.class})
    public ResponseEntity<String> handleStateChangeException(StateChangeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UnverifiedUserException.class})
    public ResponseEntity<String> handleUnverifiedUserException(UnverifiedUserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {ReplyToArchivedPostException.class})
    public ResponseEntity<String> handleReplyToArchivedPostException(ReplyToArchivedPostException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

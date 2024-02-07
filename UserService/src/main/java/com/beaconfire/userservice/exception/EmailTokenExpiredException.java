package com.beaconfire.userservice.exception;

public class EmailTokenExpiredException extends RuntimeException {
    public EmailTokenExpiredException(String message) {
        super(message);
    }
}

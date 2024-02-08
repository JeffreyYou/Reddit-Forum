package com.beaconfire.userservice.exception;

public class EmailTokenNotFoundException extends RuntimeException {

    public EmailTokenNotFoundException(String message) {
        super(message);
    }
}

package com.beaconfire.userservice.exception;

public class EmailVerifiedAndUnchangedException extends RuntimeException {
    public EmailVerifiedAndUnchangedException(String message) {
        super(message);
    }
}

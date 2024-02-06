package com.beaconfire.userservice.exception;

public class UserFieldNotFoundException extends RuntimeException {

    public UserFieldNotFoundException(String message) {
        super(message);
    }
}

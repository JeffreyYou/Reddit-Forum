package com.beaconfire.postandreplyservice.exception;

public class NonOwnerPostException extends RuntimeException {
    public NonOwnerPostException(String message) {
        super(message);
    }
}

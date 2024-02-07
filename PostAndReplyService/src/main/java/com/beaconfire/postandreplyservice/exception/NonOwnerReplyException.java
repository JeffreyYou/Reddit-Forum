package com.beaconfire.postandreplyservice.exception;

public class NonOwnerReplyException extends RuntimeException {
    public NonOwnerReplyException(String message) {
        super(message);
    }
}

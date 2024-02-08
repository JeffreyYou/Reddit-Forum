package com.beaconfire.postandreplyservice.exception;

public class ReplyToArchivedPostException extends RuntimeException {
    public ReplyToArchivedPostException(String message) {
        super(message);
    }
}

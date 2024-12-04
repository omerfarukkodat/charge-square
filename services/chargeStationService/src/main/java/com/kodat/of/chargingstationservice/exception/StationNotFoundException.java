package com.kodat.of.chargingstationservice.exception;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message) {
        super(message);
    }
}

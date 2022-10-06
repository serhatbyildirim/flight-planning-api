package com.sisal.flightplanningapi.exception;

public class ConflictedFlightException extends RuntimeException {

    public ConflictedFlightException() {
        super("Conflicted Flight occurred");
    }
}

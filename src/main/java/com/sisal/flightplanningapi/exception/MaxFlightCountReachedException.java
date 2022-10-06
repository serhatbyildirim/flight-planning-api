package com.sisal.flightplanningapi.exception;

public class MaxFlightCountReachedException extends RuntimeException {

    public MaxFlightCountReachedException(String source, String destination) {
        super("Max Flight Count Reached with source : " + source + " and destination " + destination);
    }
}

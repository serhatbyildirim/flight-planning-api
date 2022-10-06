package com.sisal.flightplanningapi.controller.advice;

import com.sisal.flightplanningapi.exception.ConflictedFlightException;
import com.sisal.flightplanningapi.exception.MaxFlightCountReachedException;
import com.sisal.flightplanningapi.model.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MaxFlightCountReachedException.class)
    public ResponseEntity<ErrorResponse> handle(MaxFlightCountReachedException exception) {
        log.error("Max Flight Count Reached Exception occurred.", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                "MaxFlightCountReachedException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConflictedFlightException.class)
    public ResponseEntity<ErrorResponse> handle(ConflictedFlightException exception) {
        log.error("Conflicted Flight Exception occurred.", exception);
        ErrorResponse errorResponse = new ErrorResponse(
                "ConflictedFlightException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}

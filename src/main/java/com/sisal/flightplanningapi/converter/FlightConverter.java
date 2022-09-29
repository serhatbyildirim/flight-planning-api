package com.sisal.flightplanningapi.converter;

import com.sisal.flightplanningapi.domain.Flight;
import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class FlightConverter implements Function<FlightAddRequest, Flight> {

    @Override
    public Flight apply(FlightAddRequest flightAddRequest) {
        return Flight.builder()
                    .airlineCode(flightAddRequest.getAirlineCode())
                    .sourceAirportCode(flightAddRequest.getSourceAirportCode())
                    .destinationAirportCode(flightAddRequest.getDestinationAirportCode())
                    .durationMinute(flightAddRequest.getDurationMinute())
                .build();
    }
}

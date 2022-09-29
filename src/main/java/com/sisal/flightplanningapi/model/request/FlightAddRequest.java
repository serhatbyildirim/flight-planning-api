package com.sisal.flightplanningapi.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightAddRequest {

    private String airlineCode;
    private String sourceAirportCode;
    private String destinationAirportCode;
    private Integer durationMinute;
}

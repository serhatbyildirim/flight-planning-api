package com.sisal.flightplanningapi.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FlightAddRequest {

    private String airlineCode;
    private String sourceAirportCode;
    private String destinationAirportCode;
    private Integer durationMinute;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Europe/Istanbul")
    private Date flightDate;
}

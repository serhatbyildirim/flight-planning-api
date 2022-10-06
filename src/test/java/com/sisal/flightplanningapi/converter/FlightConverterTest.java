package com.sisal.flightplanningapi.converter;

import com.sisal.flightplanningapi.domain.Flight;
import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FlightConverterTest {

    @InjectMocks
    private FlightConverter converter;

    @Test
    public void it_should_convert() {

        FlightAddRequest flightAddRequest = new FlightAddRequest();
        flightAddRequest.setAirlineCode("code");
        flightAddRequest.setSourceAirportCode("sa");
        flightAddRequest.setDestinationAirportCode("as");
        flightAddRequest.setDurationMinute(60);

        Flight flight = converter.apply(flightAddRequest);

        assertEquals(flightAddRequest.getAirlineCode(), flight.getAirlineCode());
        assertEquals(flightAddRequest.getDestinationAirportCode(), flight.getDestinationAirportCode());
        assertEquals(flightAddRequest.getSourceAirportCode(), flight.getSourceAirportCode());
        assertEquals(flightAddRequest.getDurationMinute(), flight.getDurationMinute());
    }
}
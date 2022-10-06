package com.sisal.flightplanningapi.repository;

import com.sisal.flightplanningapi.domain.Flight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void it_should_save_and_findByAirlineCode() {
        Flight flight1 = Flight.builder().airlineCode("code").build();

        flightRepository.save(flight1);
        List<Flight> foundedFlight = flightRepository.findByAirlineCode("code");

        Assertions.assertNotNull(foundedFlight);
        Assertions.assertEquals(foundedFlight.get(0).getAirlineCode(), "code");
    }

    @Test
    public void it_should_save_and_findBySourceAirportCodeAndDestinationAirportCode() {
        Flight flight1 = Flight.builder().airlineCode("code").sourceAirportCode("sa").destinationAirportCode("as").durationMinute(60).build();

        flightRepository.save(flight1);
        List<Flight> foundedFlight = flightRepository.findBySourceAirportCodeAndDestinationAirportCode("sa","as");

        Assertions.assertNotNull(foundedFlight);
        Assertions.assertEquals(foundedFlight.get(0).getAirlineCode(), "code");
        Assertions.assertEquals(foundedFlight.get(0).getSourceAirportCode(), "sa");
        Assertions.assertEquals(foundedFlight.get(0).getDestinationAirportCode(), "as");
        Assertions.assertEquals(foundedFlight.get(0).getDurationMinute(), 60);
    }

}
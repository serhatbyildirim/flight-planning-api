package com.sisal.flightplanningapi.repository;

import com.sisal.flightplanningapi.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByAirlineCode(String airlineCode);

    List<Flight> findBySourceAirportCodeAndDestinationAirportCode(String source, String destination);
}

package com.sisal.flightplanningapi.repository;

import com.sisal.flightplanningapi.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}

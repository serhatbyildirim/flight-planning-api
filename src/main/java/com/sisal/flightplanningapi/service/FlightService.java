package com.sisal.flightplanningapi.service;

import com.sisal.flightplanningapi.converter.FlightConverter;
import com.sisal.flightplanningapi.domain.Flight;
import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import com.sisal.flightplanningapi.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightConverter flightConverter;
    private final FlightRepository flightRepository;

    public void addFlight(FlightAddRequest flightAddRequest) {
        Flight flight = flightConverter.apply(flightAddRequest);
        flightRepository.save(flight);
    }
}

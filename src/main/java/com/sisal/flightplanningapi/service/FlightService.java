package com.sisal.flightplanningapi.service;

import com.sisal.flightplanningapi.converter.FlightConverter;
import com.sisal.flightplanningapi.domain.Flight;
import com.sisal.flightplanningapi.exception.ConflictedFlightException;
import com.sisal.flightplanningapi.exception.MaxFlightCountReachedException;
import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import com.sisal.flightplanningapi.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {

    public static final int MAX_FLIGHT_COUNT = 3;
    private final FlightConverter flightConverter;
    private final FlightRepository flightRepository;

    public synchronized void addFlight(FlightAddRequest request) {

        List<Flight> sameRouteFlightList = flightRepository.findBySourceAirportCodeAndDestinationAirportCode(request.getSourceAirportCode(), request.getDestinationAirportCode());

        if (sameRouteFlightList.size() >= MAX_FLIGHT_COUNT) {
            throw new MaxFlightCountReachedException(request.getSourceAirportCode(), request.getDestinationAirportCode());
        }

        Date requestedFlightDate = request.getFlightDate();
        DateTime endRequestedFlightTime = new DateTime(requestedFlightDate).plusMinutes(request.getDurationMinute());

        List<Flight> allFlights = flightRepository.findAll();

        boolean hasConflictedWithOngoingFlights = allFlights.stream()
                .anyMatch(f -> {
                    DateTime flightDateTime = new DateTime(f.getFlightDate());
                    return flightDateTime.isBefore(requestedFlightDate.getTime()) &&
                            flightDateTime.plusMinutes(f.getDurationMinute()).isAfter(requestedFlightDate.getTime());
                });

        boolean hasConflictedWithNotStartedFlights = allFlights.stream()
                .anyMatch(f -> {
                    DateTime flightDateTime = new DateTime(f.getFlightDate());
                    return flightDateTime.isAfter(requestedFlightDate.getTime()) &&
                            flightDateTime.isBefore(endRequestedFlightTime);
                });

        if (hasConflictedWithOngoingFlights || hasConflictedWithNotStartedFlights) {
            throw new ConflictedFlightException();
        }

        Flight flight = flightConverter.apply(request);
        flightRepository.save(flight);
    }

    public List<Flight> getFlights(String airlineCode) {
        return flightRepository.findByAirlineCode(airlineCode);
    }
}

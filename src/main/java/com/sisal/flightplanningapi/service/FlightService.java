package com.sisal.flightplanningapi.service;

import com.sisal.flightplanningapi.converter.FlightConverter;
import com.sisal.flightplanningapi.domain.Flight;
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

    public void addFlight(FlightAddRequest flightAddRequest) {

        List<Flight> sameRouteFlightList = flightRepository.findBySourceAirportCodeAndDestinationAirportCode(flightAddRequest.getSourceAirportCode(), flightAddRequest.getDestinationAirportCode());

        if (sameRouteFlightList.size() > MAX_FLIGHT_COUNT) {
            log.error("Max flight count has reached");
            return;
        }

        Date requestedFlightDate = flightAddRequest.getFlightDate();
        DateTime endRequestedFlightTime = new DateTime(requestedFlightDate).plusMinutes(flightAddRequest.getDurationMinute());

        List<Flight> allFlights = flightRepository.findAll();

        boolean hasConflictedWithOngoingFlights = allFlights.stream()
                .noneMatch(f -> {
                    DateTime flightDateTime = new DateTime(f.getFlightDate());
                    return flightDateTime.isBefore(requestedFlightDate.getTime()) && flightDateTime.plusMinutes(f.getDurationMinute()).isBefore(requestedFlightDate.getTime());
                });

        boolean hasConflictedWithNotStartedFlights = allFlights.stream()
                .noneMatch(f -> {
                    DateTime flightDateTime = new DateTime(f.getFlightDate());
                    return flightDateTime.isAfter(requestedFlightDate.getTime()) && flightDateTime.isAfter(endRequestedFlightTime);
                });

        if (hasConflictedWithOngoingFlights || hasConflictedWithNotStartedFlights) {
            return;
        }

        Flight flight = flightConverter.apply(flightAddRequest);
        flightRepository.save(flight);
    }

    public List<Flight> getFlights(String airlineCode) {
        return flightRepository.findByAirlineCode(airlineCode);
    }
}

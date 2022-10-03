package com.sisal.flightplanningapi.controller;

import com.sisal.flightplanningapi.domain.Flight;
import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import com.sisal.flightplanningapi.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFlight(@RequestBody FlightAddRequest flightAddRequest) {
        flightService.addFlight(flightAddRequest);
    }

    @GetMapping
    public List<Flight> getFlights(@RequestParam String airlineCode) {
        return flightService.getFlights(airlineCode);
    }

}

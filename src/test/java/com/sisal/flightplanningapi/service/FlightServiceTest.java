package com.sisal.flightplanningapi.service;

import com.sisal.flightplanningapi.converter.FlightConverter;
import com.sisal.flightplanningapi.domain.Flight;
import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import com.sisal.flightplanningapi.repository.FlightRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static java.util.Arrays.asList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightService service;

    @Mock
    private FlightRepository repository;

    @Mock
    private FlightConverter converter;

    @Test
    public void it_should_add_flight() {

        FlightAddRequest flightAddRequest = new FlightAddRequest();
        DateTime dateTime = new DateTime(2022, 9, 3, 20, 0);
        flightAddRequest.setFlightDate(dateTime.toDate());
        flightAddRequest.setDurationMinute(30);
        flightAddRequest.setAirlineCode("code1");
        flightAddRequest.setSourceAirportCode("sa");
        flightAddRequest.setDestinationAirportCode("as");

        Flight flight1 = new Flight();
        DateTime dateTime1 = new DateTime(2022, 9, 3, 19, 00);
        flight1.setFlightDate(dateTime1.toDate());
        flight1.setDurationMinute(30);

        Flight flight2 = new Flight();
        DateTime dateTime2 = new DateTime(2022, 9, 3, 20, 45);
        flight2.setFlightDate(dateTime2.toDate());
        flight2.setDurationMinute(30);

        Flight flight3 = new Flight();
        DateTime dateTime3 = new DateTime(2022, 9, 3, 21, 0);
        flight3.setFlightDate(dateTime3.toDate());
        flight3.setDurationMinute(30);

        given(repository.findBySourceAirportCodeAndDestinationAirportCode("sa", "as")).willReturn(asList(flight1, flight2));
        given(repository.findAll()).willReturn(asList(flight1, flight2, flight3));
        Flight savedFlight = new Flight();
        given(converter.apply(flightAddRequest)).willReturn(savedFlight);

        // when
        service.addFlight(flightAddRequest);

        // then
        verify(repository).findBySourceAirportCodeAndDestinationAirportCode("sa", "as");
        verify(repository).findAll();
        verify(converter).apply(flightAddRequest);
        verify(repository).save(savedFlight);
    }

    @Test
    public void it_should_not_add_flight_when_has_3_flights_on_same_direction() {
        // given

        FlightAddRequest flightAddRequest = new FlightAddRequest();
        DateTime dateTime = new DateTime(2022, 9, 3, 20, 0);

        flightAddRequest.setFlightDate(dateTime.toDate());
        flightAddRequest.setAirlineCode("code1");
        flightAddRequest.setDurationMinute(60);
        flightAddRequest.setSourceAirportCode("sa");
        flightAddRequest.setDestinationAirportCode("as");

        Flight flight1 = new Flight();
        Flight flight2 = new Flight();
        Flight flight3 = new Flight();
        Flight flight4 = new Flight();
        given(repository.findBySourceAirportCodeAndDestinationAirportCode("sa", "as")).willReturn(asList(flight1, flight2, flight3, flight4));

        // when
        service.addFlight(flightAddRequest);

        // then
        verify(repository).findBySourceAirportCodeAndDestinationAirportCode("sa", "as");
        verifyNoInteractions(converter);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void it_should_not_add_flight_when_has_conflict_with_not_started_flight() {

        FlightAddRequest flightAddRequest = new FlightAddRequest();
        DateTime dateTime = new DateTime(2022, 9, 3, 20, 0);
        flightAddRequest.setFlightDate(dateTime.toDate());
        flightAddRequest.setDurationMinute(90);
        flightAddRequest.setAirlineCode("code1");
        flightAddRequest.setSourceAirportCode("sa");
        flightAddRequest.setDestinationAirportCode("as");

        Flight flight1 = new Flight();
        DateTime dateTime1 = new DateTime(2022, 9, 3, 20, 30);
        flight1.setFlightDate(dateTime1.toDate());
        flight1.setDurationMinute(15);

        Flight flight2 = new Flight();
        DateTime dateTime2 = new DateTime(2022, 9, 3, 21, 0);
        flight2.setFlightDate(dateTime2.toDate());
        flight2.setDurationMinute(30);

        Flight flight3 = new Flight();
        DateTime dateTime3 = new DateTime(2022, 9, 3, 21, 30);
        flight3.setFlightDate(dateTime3.toDate());
        flight3.setDurationMinute(45);

        given(repository.findBySourceAirportCodeAndDestinationAirportCode("sa", "as")).willReturn(asList(flight1, flight2));
        given(repository.findAll()).willReturn(asList(flight1, flight2, flight3));

        // when
        service.addFlight(flightAddRequest);

        // then
        verify(repository).findBySourceAirportCodeAndDestinationAirportCode("sa", "as");
        verify(repository).findAll();
        verifyNoInteractions(converter);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void it_should_not_add_flight_when_has_conflict_with_ongoing_flight() {

        FlightAddRequest flightAddRequest = new FlightAddRequest();
        DateTime dateTime = new DateTime(2022, 9, 3, 20, 0);
        flightAddRequest.setFlightDate(dateTime.toDate());
        flightAddRequest.setDurationMinute(90);
        flightAddRequest.setAirlineCode("code1");
        flightAddRequest.setSourceAirportCode("sa");
        flightAddRequest.setDestinationAirportCode("as");

        Flight flight1 = new Flight();
        DateTime dateTime1 = new DateTime(2022, 9, 3, 18, 30);
        flight1.setFlightDate(dateTime1.toDate());
        flight1.setDurationMinute(120);

        Flight flight2 = new Flight();
        DateTime dateTime2 = new DateTime(2022, 9, 3, 19, 30);
        flight2.setFlightDate(dateTime2.toDate());
        flight2.setDurationMinute(45);

        Flight flight3 = new Flight();
        DateTime dateTime3 = new DateTime(2022, 9, 3, 19, 30);
        flight3.setFlightDate(dateTime3.toDate());
        flight3.setDurationMinute(15);

        given(repository.findBySourceAirportCodeAndDestinationAirportCode("sa", "as")).willReturn(asList(flight1, flight2));
        given(repository.findAll()).willReturn(asList(flight1, flight2, flight3));

        // when
        service.addFlight(flightAddRequest);

        // then
        verify(repository).findBySourceAirportCodeAndDestinationAirportCode("sa", "as");
        verify(repository).findAll();
        verifyNoInteractions(converter);
        verifyNoMoreInteractions(repository);
    }
}
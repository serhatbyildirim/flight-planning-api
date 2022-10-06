package com.sisal.flightplanningapi.controller;

import com.sisal.flightplanningapi.model.request.FlightAddRequest;
import com.sisal.flightplanningapi.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FlightController.class)
public class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Test
    public void it_should_create() throws Exception {
        // given
        // when
        mockMvc.perform(post("/flight")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"airlineCode\": \"code1\",\n" +
                                "  \"destinationAirportCode\": \"destination\",\n" +
                                "  \"sourceAirportCode\": \"source\",\n" +
                                "  \"durationMinute\": 60,\n" +
                                "  \"flightDate\": \"2022-10-06 10:00:00\"\n" +
                                "}"))
                .andExpect(status().isCreated());

        // then
        ArgumentCaptor<FlightAddRequest> requestCaptor = ArgumentCaptor.forClass(FlightAddRequest.class);
        verify(flightService).addFlight(requestCaptor.capture());
        FlightAddRequest capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.getAirlineCode()).isEqualTo("code1");
        assertThat(capturedRequest.getDestinationAirportCode()).isEqualTo("destination");
        assertThat(capturedRequest.getSourceAirportCode()).isEqualTo("source");
        assertThat(capturedRequest.getDurationMinute()).isEqualTo(60);
    }

    @Test
    public void it_should_get_leads() throws Exception {
        // given
        // when
        mockMvc.perform(get("/flight?airlineCode=code1"))
                .andExpect(status().isOk());

        // then
        ArgumentCaptor<String> requestCaptor = ArgumentCaptor.forClass(String.class);
        verify(flightService).getFlights(requestCaptor.capture());
        String capturedCode = requestCaptor.getValue();
        assertThat(capturedCode).isEqualTo("code1");
    }


}
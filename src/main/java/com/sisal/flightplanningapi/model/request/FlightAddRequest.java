package com.sisal.flightplanningapi.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class FlightAddRequest {

    @NotBlank(message = "{airlineCode.notBlank}")
    private String airlineCode;

    @NotBlank(message = "{sourceAirportCode.notBlank}")
    private String sourceAirportCode;

    @NotBlank(message = "{destinationAirportCode.notBlank}")
    private String destinationAirportCode;

    @NotNull(message = "{durationMinute.notNull}")
    private Integer durationMinute;

    @NotNull(message = "{flightDate.notNull}")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
    private Date flightDate;
}

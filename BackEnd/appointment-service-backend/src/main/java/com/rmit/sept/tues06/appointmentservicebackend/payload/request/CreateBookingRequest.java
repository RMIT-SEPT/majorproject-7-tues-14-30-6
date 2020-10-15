package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CreateBookingRequest {
    @NotBlank
    private String serviceName;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime bookingDateTime;

    @Schema(example = "Dentist appointment", description = "Which service the booking is for")
    public String getServiceName() {
        return serviceName;
    }

    @Schema(description = "Timeslot of the booking")
    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }
}

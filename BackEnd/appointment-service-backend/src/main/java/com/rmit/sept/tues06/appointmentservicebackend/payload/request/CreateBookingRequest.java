package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateBookingRequest {
    @NotNull
    private Long customerId;

    @NotBlank
    private String serviceName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateTime;

    @Schema(example = "1", description = "username of the user")
    public Long getCustomerId() {
        return customerId;
    }

    @Schema(example = "Dentist appointment", description = "Which service the booking is for")
    public String getServiceName() {
        return serviceName;
    }

    @Schema(example = "2020-09-16T14:55:40.154+00:00", description = "Timeslot of the booking")
    public Date getBookingDateTime() {
        return bookingDateTime;
    }
}

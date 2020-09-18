package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

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

    public Long getCustomerId() {
        return customerId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Date getBookingDateTime() {
        return bookingDateTime;
    }
}

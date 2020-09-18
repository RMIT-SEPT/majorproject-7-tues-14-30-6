package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import javax.validation.constraints.NotNull;

public class CancelBookingRequest {
    @NotNull
    private Long bookingId;

    public Long getBookingId() {
        return bookingId;
    }
}

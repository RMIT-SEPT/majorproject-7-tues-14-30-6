package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class CancelBookingRequest {
    @NotNull
    private Long bookingId;

    @Schema(description = "ID of the booking to be cancelled")
    public Long getBookingId() {
        return bookingId;
    }
}

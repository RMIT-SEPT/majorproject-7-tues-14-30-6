package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AssignWorkerRequest {
    @NotNull
    private Long bookingId;

    @NotNull
    private Long workerId;

    @Schema(description = "Booking Id")
    public Long getBookingId() {
        return bookingId;
    }

    @Schema(description = "Worker Id")
    public Long getWorkerId() {
        return workerId;
    }
}

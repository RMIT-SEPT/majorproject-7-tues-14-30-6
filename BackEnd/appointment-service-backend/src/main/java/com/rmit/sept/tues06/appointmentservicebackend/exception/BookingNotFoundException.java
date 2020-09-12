package com.rmit.sept.tues06.appointmentservicebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends ResourceNotFoundException {

    public BookingNotFoundException(String bookingId) {
        super("Booking", "id", bookingId);
    }
}

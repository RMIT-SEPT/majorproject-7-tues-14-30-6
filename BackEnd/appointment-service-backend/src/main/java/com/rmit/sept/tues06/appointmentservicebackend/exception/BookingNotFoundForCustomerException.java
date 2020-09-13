package com.rmit.sept.tues06.appointmentservicebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundForCustomerException extends ResourceNotFoundException {
    public BookingNotFoundForCustomerException(String username) {
        super("Bookings for customer", "username", username);
    }
}

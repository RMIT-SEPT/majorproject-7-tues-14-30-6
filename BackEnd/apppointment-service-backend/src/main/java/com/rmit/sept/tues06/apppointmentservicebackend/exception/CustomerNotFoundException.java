package com.rmit.sept.tues06.apppointmentservicebackend.exception;

public class CustomerNotFoundException extends Exception {
    private long userId;
    public CustomerNotFoundException(long userId) {
        super(String.format("Customer was not found with id : '%s'", userId));
    }

}

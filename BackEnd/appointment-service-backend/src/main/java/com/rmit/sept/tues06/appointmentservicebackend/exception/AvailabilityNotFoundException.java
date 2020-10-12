package com.rmit.sept.tues06.appointmentservicebackend.exception;

public class AvailabilityNotFoundException extends ResourceNotFoundException {
    public AvailabilityNotFoundException(String availabilityId) {
        super("Availability", "id", availabilityId);
    }
}

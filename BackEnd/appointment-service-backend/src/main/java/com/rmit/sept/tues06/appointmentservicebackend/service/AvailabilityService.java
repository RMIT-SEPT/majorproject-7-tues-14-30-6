package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.Availability;

public interface AvailabilityService {
    Availability findById(Long id);

    Availability createAvailability(Availability availability);

    Availability updateAvailability(Long id, Availability availability);

    Availability removeAvailability(Long id);
}

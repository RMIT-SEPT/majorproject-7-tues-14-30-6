package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.AvailabilityNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Availability;
import com.rmit.sept.tues06.appointmentservicebackend.repository.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Override
    public Availability findById(Long id) {
        return availabilityRepository.findById(id).orElseThrow(() -> new AvailabilityNotFoundException(id + ""));
    }

    @Override
    public Availability createAvailability(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public Availability updateAvailability(Long id, Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public Availability removeAvailability(Long id) {
        Availability availability = findById(id);
        availabilityRepository.delete(availability);

        return availability;
    }
}

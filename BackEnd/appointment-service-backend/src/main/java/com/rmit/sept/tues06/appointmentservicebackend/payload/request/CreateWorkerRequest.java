package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import com.rmit.sept.tues06.appointmentservicebackend.model.Availability;

import java.util.List;

public class CreateWorkerRequest extends SignupRequest {
    private List<Availability> availabilities;

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }
}

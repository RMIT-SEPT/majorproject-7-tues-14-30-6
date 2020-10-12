package com.rmit.sept.tues06.appointmentservicebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Worker extends User {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Availability> availabilities = new ArrayList<>();

    public Worker(String username, String email, String password, String name, String address, String phoneNumber) {
        super(username, email, password, name, address, phoneNumber);
    }

    public Worker() {
        super();
    }

    public void addAvailability(Availability availability) {
        this.availabilities.add(availability);
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public boolean hasAvailability(Long availabilityId) {
        return availabilities.stream().anyMatch(a -> a.getId().equals(availabilityId));
    }
}

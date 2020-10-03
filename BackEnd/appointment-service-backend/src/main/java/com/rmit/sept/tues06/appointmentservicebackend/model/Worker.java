package com.rmit.sept.tues06.appointmentservicebackend.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Worker extends User {
    @OneToMany(mappedBy = "users")
    private List<Availability> availabilities;

    public Worker(String username, String email, String password, String name, String address, String phoneNumber, List<Availability> availabilities) {
        super(username, email, password, name, address, phoneNumber);
        this.availabilities = availabilities;
    }

    public Worker() {
        super();
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }
}

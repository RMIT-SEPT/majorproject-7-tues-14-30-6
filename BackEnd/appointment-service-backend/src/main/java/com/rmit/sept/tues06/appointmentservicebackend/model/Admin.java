package com.rmit.sept.tues06.appointmentservicebackend.model;

import javax.persistence.Entity;

@Entity
public class Admin extends User {
    public Admin(String username, String email, String password, String name, String address, String phoneNumber) {
        super(username, email, password, name, address, phoneNumber);
    }

    public Admin() {
        super();
    }
}

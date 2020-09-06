package com.rmit.sept.tues06.appointmentservicebackend.model;


import javax.persistence.Entity;

@Entity
public class Customer extends User {
    public Customer(String username, String email, String password, String name, String address, String phoneNumber) {
        super(username, email, password, name, address, phoneNumber);
    }

    public Customer() {
        super();
    }
}
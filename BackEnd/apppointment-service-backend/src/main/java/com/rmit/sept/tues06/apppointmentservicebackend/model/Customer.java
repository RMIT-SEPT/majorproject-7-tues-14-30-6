package com.rmit.sept.tues06.apppointmentservicebackend.model;


import javax.persistence.*;

@Entity
public class Customer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer() {
    }
}
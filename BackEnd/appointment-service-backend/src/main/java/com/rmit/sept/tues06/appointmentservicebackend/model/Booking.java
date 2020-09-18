package com.rmit.sept.tues06.appointmentservicebackend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private String serviceName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateTime;

    @Schema(example = "true", description = "Booking status (active, cancelled)")
    private boolean isActive;

    @Schema(description = "Customer associated with the booking")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Schema(example = "Dentist appointment", description = "Which service the booking is for")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceType) {
        this.serviceName = serviceType;
    }

    @Schema(example = "2020-09-16T14:55:40.154+00:00", description = "Timeslot of the booking")
    public Date getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(Date date) {
        this.bookingDateTime = date;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    @ManyToOne
    private Customer customer;

    private String serviceName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingDateTime;

    @Schema(example = "true", description = "Booking status (active, cancelled)")
    private boolean isActive;

    @Schema(description = "")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Schema(example = "Service name", description = "")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceType) {
        this.serviceName = serviceType;
    }

    @Schema(description = "")
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

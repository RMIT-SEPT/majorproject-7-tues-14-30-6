package com.rmit.sept.tues06.appointmentservicebackend.model;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceType) {
        this.serviceName = serviceType;
    }

    public Date getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(Date date) {
        this.bookingDateTime = date;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    private String serviceName;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime bookingDateTime;

    @Schema(example = "true", description = "Booking status (active, cancelled)")
    private boolean isActive;

    @Schema(description = "Customer who booked the timeslot")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Schema(description = "Worker assigned to the booking timeslot")
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Schema(example = "Dentist appointment", description = "Which service the booking is for")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceType) {
        this.serviceName = serviceType;
    }

    @Schema(description = "Timeslot of the booking")
    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime date) {
        this.bookingDateTime = date;
    }

    public boolean isActive() {
        return isActive;
    }

    @Schema(example = "true if booking is active, false if cancelled", description = "The status of the booking")
    public void setActive(boolean active) {
        isActive = active;
    }
}

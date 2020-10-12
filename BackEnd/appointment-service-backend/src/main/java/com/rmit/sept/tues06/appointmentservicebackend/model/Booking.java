package com.rmit.sept.tues06.appointmentservicebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Worker worker;

    private String serviceName;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime bookingDateTime;

    private boolean isActive;

    @Schema(description = "Id of customer who booked the timeslot")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Schema(description = "Id of worker assigned to the booking")
    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Schema(example = "John Smith", description = "Name of worker assigned to the booking")
    public String getWorkerName() {
        String name = "";

        if (worker != null)
            name = worker.getName();

        return name;
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

    @Schema(example = "true", description = "Booking status (true if active, false if cancelled)")
    public boolean isActive() {
        return isActive;
    }

    @Schema(example = "true if booking is active, false if cancelled", description = "The status of the booking")
    public void setActive(boolean active) {
        isActive = active;
    }
}

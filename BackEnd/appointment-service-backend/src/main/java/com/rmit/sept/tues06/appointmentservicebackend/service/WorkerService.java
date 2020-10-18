package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;

import java.time.LocalDateTime;

public interface WorkerService {
    boolean isAvailable(Worker worker, LocalDateTime bookingDate);
}

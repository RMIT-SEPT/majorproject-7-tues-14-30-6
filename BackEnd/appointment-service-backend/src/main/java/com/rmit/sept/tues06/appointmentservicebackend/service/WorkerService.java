package com.rmit.sept.tues06.appointmentservicebackend.service;

import java.time.LocalDateTime;

public interface WorkerService {
    boolean isAvailable(LocalDateTime dateTime);
}

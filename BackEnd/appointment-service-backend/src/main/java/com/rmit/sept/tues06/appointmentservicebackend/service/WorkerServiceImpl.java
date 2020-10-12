package com.rmit.sept.tues06.appointmentservicebackend.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkerServiceImpl implements WorkerService {


    @Override
    public boolean isAvailable(LocalDateTime dateTime) {
        // TODO
        return true;
    }
}

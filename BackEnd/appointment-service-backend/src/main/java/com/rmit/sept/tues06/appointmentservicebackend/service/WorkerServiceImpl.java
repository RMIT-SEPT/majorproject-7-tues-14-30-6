package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.Availability;
import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Override
    public boolean isAvailable(Worker worker, LocalDateTime bookingDate) {
        boolean isAvailable = false;
        boolean dayCheckedforConflicts = false;

        // if worker hasn't specified any availability days
        if (CollectionUtils.isEmpty(worker.getAvailabilities()))
            isAvailable = true;
        else {
            for (Availability availability : worker.getAvailabilities()) {
                dayCheckedforConflicts = false;
                DayOfWeek availabilityDayOfWeek = DayOfWeek.of(availability.getDayOfWeek());
                DayOfWeek bookingDayOfWeek = bookingDate.getDayOfWeek();

                if (availabilityDayOfWeek == bookingDayOfWeek) {
                    // check time range
                    LocalTime startTime = availability.getStartTime();
                    LocalTime endTime = availability.getEndTime();
                    LocalTime bookingTime = bookingDate.toLocalTime();

                    if (bookingTime.isAfter(startTime) && bookingTime.isBefore(endTime))
                        isAvailable = true;

                    dayCheckedforConflicts = true;
                }
            }

            if (!dayCheckedforConflicts)
                isAvailable = true;
        }

        return isAvailable;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

public class AddUpdateAvailabilityRequest {
    @NotBlank(message = "Week day is required")
    public String weekDay;
    @NotBlank(message = "Start time is required")
    private String startTime;
    @NotBlank(message = "End time is required")

    private String endTime;

    public String getWeekDay() {
        return weekDay;
    }

    public LocalTime getStartTime() {
        return LocalTime.parse(startTime);
    }

    public LocalTime getEndTime() {
        return LocalTime.parse(endTime);
    }
}

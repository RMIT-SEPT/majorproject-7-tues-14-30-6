package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

public class AddUpdateAvailabilityRequest {
    @NotBlank(message = "Week day is required")
    public String weekDay;
    @NotBlank(message = "Start time is required")
    private String startTime;
    @NotBlank(message = "End time is required")
    private String endTime;

    @Schema(example = "Tuesday", description = "the day of the week in string format")
    public String getWeekDay() {
        return weekDay;
    }

    @Schema(example = "08:00", description = "start time of availability for the day")
    public LocalTime getStartTime() {
        return LocalTime.parse(startTime);
    }

    @Schema(example = "08:00", description = "end time of availability for the day")
    public LocalTime getEndTime() {
        return LocalTime.parse(endTime);
    }
}

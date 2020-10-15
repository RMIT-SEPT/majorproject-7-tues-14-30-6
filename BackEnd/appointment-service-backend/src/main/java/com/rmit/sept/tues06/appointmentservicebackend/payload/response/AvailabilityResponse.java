package com.rmit.sept.tues06.appointmentservicebackend.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalTime;

public class AvailabilityResponse {
    private Long id;
    private Long workerId;
    private String weekDay;
    private LocalTime startTime;
    private LocalTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    @Schema(example = "Tuesday", description = "the day of the week in string format")
    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    @Schema(example = "08:00", description = "start time of availability for the day")
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Schema(example = "21:00", description = "end time of availability for the day")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "availabilities")
public class Availability extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User worker;

    @NotNull(message = "Day of Week is required")
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Start time is required")
    @Column(columnDefinition = "TIME")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Column(columnDefinition = "TIME")
    private LocalTime endTime;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public User getWorker() {
        return worker;
    }
}

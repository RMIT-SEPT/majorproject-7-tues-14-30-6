package com.rmit.sept.tues06.appointmentservicebackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "availabilities")
public class Availability extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    private User worker;

    @NotNull(message = "Day of Week is required")
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek; // MONDAY = 0, SUNDAY = 6

    @NotNull(message = "Start time is required")
    @Column(columnDefinition = "TIME")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Column(columnDefinition = "TIME")
    private LocalTime endTime; // TODO ADD VALIDATION - END TIME MUST BE AFTER START TIME

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

    public void setWorker(User worker) {
        this.worker = worker;
    }
}

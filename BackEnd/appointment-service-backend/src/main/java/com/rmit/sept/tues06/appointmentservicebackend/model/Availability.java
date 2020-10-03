package com.rmit.sept.tues06.appointmentservicebackend.model;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "availabilities")
public class Availability extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "id")
    private User worker;

    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    @Column(columnDefinition = "TIME")
    private LocalTime startTIme;

    @Column(columnDefinition = "TIME")
    private LocalTime endTime;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getStartTIme() {
        return startTIme;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public User getWorker() {
        return worker;
    }
}
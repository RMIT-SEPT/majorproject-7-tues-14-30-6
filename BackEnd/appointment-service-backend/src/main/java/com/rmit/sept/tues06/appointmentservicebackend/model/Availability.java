package com.rmit.sept.tues06.appointmentservicebackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "availabilities")
public class Availability extends BaseEntity {
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek dayOfWeek;

    @JsonFormat(pattern = "HH:mm")
    @Column(columnDefinition = "TIME")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    @Column(columnDefinition = "TIME")
    private LocalTime endTime; // TODO ADD VALIDATION CHECKS - END TIME MUST BE AFTER START TIME

    @Schema(example = "2", description = "the day of the week in numerical format, based on the ISO-8601 standard (AUS: Monday = 1, Sunday = 7)")
    public int getDayOfWeek() {
        return dayOfWeek.getValue();
    }

    private void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Schema(example = "Tuesday", description = "the day of the week in string format")
    public String getWeekDay() {
        return StringUtils.capitalize(dayOfWeek.name().toLowerCase());
    }

    public void setWeekDay(String weekDay) {
        setDayOfWeek(DayOfWeek.valueOf(weekDay.trim().toUpperCase()));
    }

    @Schema(description = "start time of availability for the day")
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Schema(description = "end time of availability for the day")
    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking getBooking(Long bookingId);

    List<Booking> findActivePastBookings(LocalDateTime date);

    List<Booking> findActiveCurrentBookings(LocalDateTime date);

    List<Booking> findActivePastBookingsByCustomer(LocalDateTime date, String username);

    List<Booking> findActiveCurrentBookingsByCustomer(LocalDateTime date, String username);

    Booking createBooking(Booking booking);

    Booking cancelBooking(Booking booking);
}

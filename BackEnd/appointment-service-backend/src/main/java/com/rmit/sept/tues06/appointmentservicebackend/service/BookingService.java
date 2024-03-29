package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {
    Booking findById(Long bookingId);

    List<Booking> findAvailableBookings();

    List<Booking> findAssignableBookings();

    List<Booking> findCancelledBookings();

    List<Booking> findActivePastBookings(LocalDateTime date);

    List<Booking> findActiveCurrentBookings(LocalDateTime date);

    List<Booking> findActivePastBookingsByCustomer(LocalDateTime date, String username);

    List<Booking> findActiveCurrentBookingsByCustomer(LocalDateTime date, String username);

    List<Booking> findUpcomingBookingsByWorker(LocalDateTime date, Long workerId);

    Booking createBooking(Booking booking);

    Booking updateBooking(Booking booking);

    Booking cancelBooking(Booking booking);

    Booking assignWorker(Booking booking, User worker);
}

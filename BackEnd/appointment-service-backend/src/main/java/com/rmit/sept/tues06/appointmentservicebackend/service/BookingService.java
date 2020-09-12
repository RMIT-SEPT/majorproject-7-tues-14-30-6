package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;

import java.util.Date;
import java.util.List;

public interface BookingService {
    Booking getBooking(Long bookingId);

    List<Booking> findActivePastBookings(Date date);

    List<Booking> findActiveCurrentBookings(Date date);

    List<Booking> findActivePastBookingsByCustomer(Date date, String username);

    List<Booking> findActiveCurrentBookingsByCustomer(Date date, String username);

    Booking createBooking(Booking booking);

    Booking cancelBooking(Booking booking);
}

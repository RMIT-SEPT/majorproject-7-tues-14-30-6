package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import com.rmit.sept.tues06.appointmentservicebackend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId + ""));
    }

    public List<Booking> findAllPastBookings(Date date) {
        return bookingRepository.findAllPastBookings(date);
    }

    public List<Booking> findAllCurrentBookings(Date date) {
        return bookingRepository.findAllCurrentBookings(date);
    }

    public List<Booking> findPastBookingsByCustomer(Date date, String username) {
        return bookingRepository.findPastBookingsByCustomer(date, username);
    }

    public List<Booking> findCurrentBookingsByCustomer(Date date, String username) {
        return bookingRepository.findCurrentBookingsByCustomer(date, username);
    }

    public Booking createBooking(Booking booking) {
        Booking newBooking = new Booking();
        newBooking.setBookingDateTime(booking.getBookingDateTime());
        newBooking.setCustomer(booking.getCustomer());
        newBooking.setServiceName(booking.getServiceName());

        return bookingRepository.save(newBooking);
    }
}

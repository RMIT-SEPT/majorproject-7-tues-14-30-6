package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import com.rmit.sept.tues06.appointmentservicebackend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId + ""));
    }

    @Override
    public List<Booking> findActivePastBookings(Date date) {
        return bookingRepository.findActivePastBookings(date);
    }

    @Override
    public List<Booking> findActiveCurrentBookings(Date date) {
        return bookingRepository.findActiveCurrentBookings(date);
    }

    @Override
    public List<Booking> findActivePastBookingsByCustomer(Date date, String username) {
        return bookingRepository.findActivePastBookingsByCustomer(date, username);
    }

    @Override
    public List<Booking> findActiveCurrentBookingsByCustomer(Date date, String username) {
        return bookingRepository.findActiveCurrentBookingsByCustomer(date, username);
    }

    @Override
    public Booking createBooking(Booking booking) {
        Booking newBooking = new Booking();
        newBooking.setBookingDateTime(booking.getBookingDateTime());
        newBooking.setCustomer(booking.getCustomer());
        newBooking.setServiceName(booking.getServiceName());
        newBooking.setActive(true);

        return bookingRepository.save(newBooking);
    }

    @Override
    public Booking cancelBooking(Booking booking) {
        booking.setActive(false);

        return bookingRepository.save(booking);
    }
}
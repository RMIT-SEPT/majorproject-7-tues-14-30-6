package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;
import com.rmit.sept.tues06.appointmentservicebackend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new BookingNotFoundException(bookingId + ""));
    }

    @Override
    public List<Booking> findAvailableBookings() {
        List<Booking> bookings = bookingRepository.findBookingByCustomerNull();
        List<Booking> availableBookings = new ArrayList<>();

        for (Booking booking : bookings)
            if (booking.isActive() && booking.getBookingDateTime().isAfter(LocalDateTime.now()))
                availableBookings.add(booking);

        return availableBookings;
    }

    @Override
    public List<Booking> findAssignableBookings() {
        List<Booking> bookings = bookingRepository.findBookingByWorkerNull();
        List<Booking> availableBookings = new ArrayList<>();

        for (Booking booking : bookings)
            if (booking.isActive() && booking.getBookingDateTime().isAfter(LocalDateTime.now()))
                availableBookings.add(booking);

        return availableBookings;
    }

    @Override
    public List<Booking> findCancelledBookings() {
        return bookingRepository.findCancelledBookings();
    }

    @Override
    public List<Booking> findActivePastBookings(LocalDateTime date) {
        return bookingRepository.findActivePastBookings(date);
    }

    @Override
    public List<Booking> findActiveCurrentBookings(LocalDateTime date) {
        return bookingRepository.findActiveCurrentBookings(date);
    }

    @Override
    public List<Booking> findActivePastBookingsByCustomer(LocalDateTime date, String username) {
        return bookingRepository.findActivePastBookingsByCustomer(date, username);
    }

    @Override
    public List<Booking> findActiveCurrentBookingsByCustomer(LocalDateTime date, String username) {
        return bookingRepository.findActiveCurrentBookingsByCustomer(date, username);
    }

    @Override
    public List<Booking> findUpcomingBookingsByWorker(LocalDateTime date, Long workerId) {
        return bookingRepository.findUpcomingBookingsByWorker(date, workerId);
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
    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking cancelBooking(Booking booking) {
        booking.setActive(false);

        return bookingRepository.save(booking);
    }

    @Override
    public Booking assignWorker(Booking booking, User worker) {
        booking.setWorker((Worker) worker);

        return bookingRepository.save(booking);
    }
}

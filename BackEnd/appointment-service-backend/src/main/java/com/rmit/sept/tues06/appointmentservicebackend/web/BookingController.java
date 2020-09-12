package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundForCustomerException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import com.rmit.sept.tues06.appointmentservicebackend.service.BookingService;
import com.rmit.sept.tues06.appointmentservicebackend.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("")
    public List<Booking> getBookings(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "past") boolean includePast,
                                     @RequestParam(value = "current") boolean includeCurrentAndFuture) {
        List<Booking> bookings = new ArrayList<>();
        Date currentDateTime = new Date();

        if (!StringUtils.isEmpty(username)) {
            if (includePast)
                bookings.addAll(bookingService.findPastBookingsByCustomer(currentDateTime, username));
            if (includeCurrentAndFuture)
                bookings.addAll(bookingService.findCurrentBookingsByCustomer(currentDateTime, username));

            if (CollectionUtils.isEmpty(bookings))
                throw new BookingNotFoundForCustomerException(username);
        } else {
            if (includePast)
                bookings.addAll(bookingService.findAllPastBookings(currentDateTime));
            if (includeCurrentAndFuture)
                bookings.addAll(bookingService.findAllCurrentBookings(currentDateTime));

            if (CollectionUtils.isEmpty(bookings))
                throw new BookingNotFoundException(null);
        }

        return bookings;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(bookingService.getBooking(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addBooking(@Valid @RequestBody Booking booking, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if (errorMap != null) return errorMap;

        return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
    }

    @PostMapping(value = "/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelBooking(@Valid @RequestBody Booking booking, BindingResult result) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if (errorMap != null) return errorMap;

        return new ResponseEntity<>(bookingService.cancelBooking(booking), HttpStatus.OK);
    }
}

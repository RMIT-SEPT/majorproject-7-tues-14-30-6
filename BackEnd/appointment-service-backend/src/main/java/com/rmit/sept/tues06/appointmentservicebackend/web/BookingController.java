package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.exception.BookingNotFoundForCustomerException;
import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CancelBookingRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateBookingRequest;
import com.rmit.sept.tues06.appointmentservicebackend.service.BookingService;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Tag(name = "booking", description = "the booking API")

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    public static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all bookings", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Bookings not found", content = @Content)})
    @GetMapping("")
    public List<Booking> getBookings(@Parameter(description = "Specify username of customer whose bookings need to be fetched")
                                     @RequestParam(value = "customer", required = false) String username,
                                     @Parameter(description = "Specify whether past bookings need to be fetched", required = true)
                                     @RequestParam(value = "past") boolean includePast,
                                     @Parameter(description = "Specify whether current and future bookings need to be fetched", required = true)
                                     @RequestParam(value = "current") boolean includeCurrentAndFuture,
                                     @Parameter(description = "Specify whether cancelled bookings need to be fetched")
                                     @RequestParam(value = "cancelled", required = false) boolean includeCancelled) {
        List<Booking> bookings = new ArrayList<>();
        Date currentDateTime = new Date();

        if (StringUtils.hasText(username)) {
            User usernameMatch = null;
            try {
                usernameMatch = userService.findByUsername(username);
            } catch (UserNotFoundException userNotFoundException) {
                logger.info(userNotFoundException.getMessage());
            }

            if (usernameMatch != null) {
                if (includeCurrentAndFuture)
                    bookings.addAll(bookingService.findActiveCurrentBookingsByCustomer(currentDateTime, username));
                if (includePast)
                    bookings.addAll(bookingService.findActivePastBookingsByCustomer(currentDateTime, username));
            }

            if (CollectionUtils.isEmpty(bookings))
                throw new BookingNotFoundForCustomerException(username);
        } else {
            if (includeCurrentAndFuture)
                bookings.addAll(bookingService.findActiveCurrentBookings(currentDateTime));
            if (includePast)
                bookings.addAll(bookingService.findActivePastBookings(currentDateTime));

            // TODO additional filtering for cancelled bookings
//            if (includeCancelled)

            if (CollectionUtils.isEmpty(bookings))
                throw new BookingNotFoundException(null);
        }

        return bookings;
    }

    @Operation(summary = "Get booking by id", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)})
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBookingById(@Parameter(description = "The booking id that needs to be fetched.", required = true) @PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(bookingService.getBooking(id), HttpStatus.OK);
    }

    @Operation(summary = "Add a booking", description = "This can only be done by a logged in customer", tags = {"booking"}, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
    })
    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addBooking(@Parameter(description = "Access Token") @RequestHeader(value = "x-access-token") String accessToken,
                                        @Valid @RequestBody CreateBookingRequest createBookingRequest) {
        Booking booking = new Booking();
        booking.setCustomer((Customer) userService.findById(createBookingRequest.getCustomerId()));
        booking.setServiceName(createBookingRequest.getServiceName());
        booking.setBookingDateTime(createBookingRequest.getBookingDateTime());

        return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
    }

    @Operation(summary = "Cancel a booking", description = "This can only be done by a logged in customer", tags = {"booking"}, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    @PostMapping(value = "/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelBooking(@Parameter(description = "Access Token") @RequestHeader(value = "x-access-token") String accessToken,
                                           @Valid @RequestBody CancelBookingRequest cancelBookingRequest) {
        Booking booking = bookingService.getBooking(cancelBookingRequest.getBookingId());
        return new ResponseEntity<>(bookingService.cancelBooking(booking), HttpStatus.OK);
    }
}

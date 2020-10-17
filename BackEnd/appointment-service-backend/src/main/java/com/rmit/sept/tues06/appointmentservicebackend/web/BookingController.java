package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.*;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.AssignWorkerRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CancelBookingRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateBookingRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateCustomerBookingRequest;
import com.rmit.sept.tues06.appointmentservicebackend.security.jwt.JwtUtils;
import com.rmit.sept.tues06.appointmentservicebackend.service.BookingService;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import com.rmit.sept.tues06.appointmentservicebackend.service.WorkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Booking")

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    public static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Get all bookings", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Booking.class)))})
    })
    @GetMapping("")
    public List<Booking> getBookings(@Parameter(description = "Specify username of customer whose bookings need to be fetched")
                                     @RequestParam(value = "customer", required = false) String username,
                                     @Parameter(description = "Specify whether past bookings need to be fetched")
                                     @RequestParam(defaultValue = "true", value = "past", required = false) boolean includePast,
                                     @Parameter(description = "Specify whether current and future bookings need to be fetched")
                                     @RequestParam(defaultValue = "true", value = "current", required = false) boolean includeCurrentAndFuture,
                                     @Parameter(description = "Specify whether cancelled bookings need to be fetched")
                                     @RequestParam(defaultValue = "false", value = "cancelled", required = false) boolean includeCancelled) {
        List<Booking> bookings = new ArrayList<>();
        LocalDateTime currentDateTime = LocalDateTime.now();

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

        } else {
            if (includeCurrentAndFuture)
                bookings.addAll(bookingService.findActiveCurrentBookings(currentDateTime));
            if (includePast)
                bookings.addAll(bookingService.findActivePastBookings(currentDateTime));
            if (includeCancelled)
                bookings.addAll(bookingService.findCancelledBookings());
        }

        return bookings;
    }

    @Operation(summary = "Get bookings that can be booked by a customer", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Booking.class)))})
    })
    @GetMapping("/available")
    public List<Booking> getAvailableBookings() {
        return bookingService.findAvailableBookings();
    }

    @Operation(summary = "Get bookings that can be assigned to workers", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Booking.class)))})
    })
    @GetMapping("/assignable")
    public List<Booking> getAssignableBookings() {
        return bookingService.findAssignableBookings();
    }


    @Operation(summary = "Get booking by id", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBookingById(@Parameter(description = "Booking Id", required = true) @PathVariable Long id) {
        return new ResponseEntity<>(bookingService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Add a booking timeslot", description = "This can only be done by an admin", tags = {"booking"}, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping(value = "/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBooking(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                        @Valid @RequestBody CreateBookingRequest createBookingRequest) {
        Booking booking = new Booking();
        booking.setServiceName(createBookingRequest.getServiceName());
        booking.setBookingDateTime(createBookingRequest.getBookingDateTime());

        // TODO CAN ONLY ADD TIMESLOT FOR FUTURE TIMES

        return new ResponseEntity<>(bookingService.createBooking(booking), HttpStatus.CREATED);
    }

    @Operation(summary = "Book a timeslot", description = "This can only be done by a customer", tags = {"booking"}, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping(value = "/book")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> addBookingForCustomer(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                                   @Valid @RequestBody CreateCustomerBookingRequest createCustomerBookingRequest) {
        Booking booking = bookingService.findById(createCustomerBookingRequest.getBookingId());
        booking.setCustomer((Customer) userService.findByUsername(jwtUtils.getUserNameFromJwtToken(accessToken.substring(accessToken.indexOf(" ")))));

        return new ResponseEntity<>(bookingService.updateBooking(booking), HttpStatus.OK);
    }

    @Operation(summary = "Cancel a booking", description = "This can only be done by a customer", tags = {"booking"}, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping(value = "/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> cancelBooking(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                           @Valid @RequestBody CancelBookingRequest cancelBookingRequest) {
        Booking booking = bookingService.findById(cancelBookingRequest.getBookingId());
        return new ResponseEntity<>(bookingService.cancelBooking(booking), HttpStatus.OK);
    }

    @Operation(summary = "Assign a worker to a booking timeslot", description = "This can only be done by an admin", tags = {"booking"},
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Booking.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Booking.class))}),
            @ApiResponse(responseCode = "404", description = "Booking or worker not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignWorker(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                          @Valid @RequestBody AssignWorkerRequest assignWorkerRequest) {
        Booking booking = bookingService.findById(assignWorkerRequest.getBookingId());
        User worker = userService.findById(assignWorkerRequest.getWorkerId());

        return new ResponseEntity<>(bookingService.assignWorker(booking, worker), HttpStatus.OK);
    }

    @Operation(summary = "Get workers who can be assigned to the booking timeslot", tags = {"booking"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = User.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = User.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Booking or worker not found", content = @Content)
    })
    @GetMapping("/{id}/availableWorkers")
    public List<User> getAvailableWorkers(@Parameter(description = "Booking Id") @PathVariable Long id) {
        List<User> availableWorkers = new ArrayList<>();
        List<User> workers = userService.findUsersByType(ERole.ROLE_WORKER);
        Booking booking = bookingService.findById(id);

        for (User worker : workers) {
            if (worker instanceof Worker)
                if (workerService.isAvailable((Worker) worker, booking.getBookingDateTime()))
                    availableWorkers.add(worker);
        }

        return availableWorkers;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.web;


import com.rmit.sept.tues06.appointmentservicebackend.exception.AvailabilitiesNotFoundForWorkerException;
import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.*;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.AddUpdateAvailabilityRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateWorkerRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.AvailabilityResponse;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.MessageResponse;
import com.rmit.sept.tues06.appointmentservicebackend.repository.RoleRepository;
import com.rmit.sept.tues06.appointmentservicebackend.service.AvailabilityService;
import com.rmit.sept.tues06.appointmentservicebackend.service.BookingService;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

@Tag(name = "Worker")

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    public static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AvailabilityService availabilityService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Operation(summary = "Get all workers", tags = {"worker"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = User.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
    })
    @GetMapping("")
    public List<User> getWorkers() {
        return userService.findUsersByType(ERole.ROLE_WORKER);
    }

    @Operation(summary = "Add a worker with their availabilities", description = "This can only be done by an admin", tags = {"worker"},
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Worker.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Worker.class))}),
            @ApiResponse(responseCode = "400", description = "Username or email is already taken", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createWorker(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                          @Valid @RequestBody CreateWorkerRequest createWorkerRequest) {
        User usernameMatch = null;
        try {
            usernameMatch = userService.findByUsername(createWorkerRequest.getUsername());
        } catch (UserNotFoundException userNotFoundException) {
            logger.info(userNotFoundException.getMessage());
        }

        if (usernameMatch != null)
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));

        User emailMatch = null;
        try {
            emailMatch = userService.findByEmail(createWorkerRequest.getEmail());
        } catch (UserNotFoundException userNotFoundException) {
            logger.info(userNotFoundException.getMessage());
        }

        if (emailMatch != null)
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));

        User user = new Worker(createWorkerRequest.getUsername(), createWorkerRequest.getEmail(), encoder.encode(createWorkerRequest.getPassword()),
                createWorkerRequest.getName(), createWorkerRequest.getAddress(), createWorkerRequest.getPhoneNumber());
        Set<Role> roles = new HashSet<>();
        Role workerRole = roleRepository.findTopByName(ERole.ROLE_WORKER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(workerRole);
        user.setRoles(roles);
        ((Worker) user).setAvailabilities(createWorkerRequest.getAvailabilities());

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Add availability entry for a worker", tags = {"worker"},
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Availability.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Availability.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/{workerId}/availability")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addAvailability(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                             @Parameter(description = "Worker Id") @PathVariable Long workerId,
                                             @Valid @RequestBody AddUpdateAvailabilityRequest addAvailabilityRequest) {
        User user = userService.findById(workerId);

        Availability availability = new Availability();
        availability.setWeekDay(addAvailabilityRequest.getWeekDay());
        availability.setStartTime(addAvailabilityRequest.getStartTime());
        availability.setEndTime(addAvailabilityRequest.getEndTime());
        ((Worker) user).addAvailability(availability);

        return new ResponseEntity<>(availabilityService.createAvailability(availability), HttpStatus.CREATED);
    }

    @Operation(summary = "Update availability entry of a worker", tags = {"worker"},
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Availability.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Availability.class))}),
            @ApiResponse(responseCode = "404", description = "Worker or availability not found", content = @Content)
    })
    @PutMapping("/{workerId}/availability/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateAvailability(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                                @Parameter(description = "Worker Id") @PathVariable Long workerId,
                                                @Valid @RequestBody AddUpdateAvailabilityRequest updateAvailabilityRequest,
                                                @Parameter(description = "Availability Id") @PathVariable Long id) {
        User user = userService.findById(workerId);

        if (!((Worker) user).hasAvailability(id))
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse("Availability id " + id + " not found for the user"));

        Availability availability = availabilityService.findById(id);
        availability.setWeekDay(updateAvailabilityRequest.getWeekDay());
        availability.setStartTime(updateAvailabilityRequest.getStartTime());
        availability.setEndTime(updateAvailabilityRequest.getEndTime());

        return new ResponseEntity<>(availabilityService.updateAvailability(id, availability), HttpStatus.OK);
    }

    @Operation(summary = "Remove availability entry for a worker", tags = {"worker"},
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Availability.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = Availability.class))}),
            @ApiResponse(responseCode = "404", description = "Worker or availability not found", content = @Content)
    })
    @DeleteMapping("/{workerId}/availability/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> removeAvailability(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken,
                                                @Parameter(description = "Worker Id") @PathVariable Long workerId,
                                                @Parameter(description = "Availability Id") @PathVariable Long id) {
        User user = userService.findById(workerId);

        if (!((Worker) user).hasAvailability(id))
            return ResponseEntity
                    .status(404)
                    .body(new MessageResponse("Availability id " + id + " not found for the user"));


        return new ResponseEntity<>(availabilityService.removeAvailability(id), HttpStatus.OK);
    }

    @Operation(summary = "Get availabilities for a worker", tags = {"worker"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Availability.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Availability.class)))}),
            @ApiResponse(responseCode = "404", description = "Worker or availability not found", content = @Content)
    })
    @GetMapping("/{workerId}/availability")
    public List<Availability> getWorkerAvailability(@Parameter(description = "Worker Id") @PathVariable Long workerId) {
        User user = userService.findById(workerId);
        List<Availability> availabilities = ((Worker) user).getAvailabilities();

        if (CollectionUtils.isEmpty(availabilities))
            throw new AvailabilitiesNotFoundForWorkerException(user.getUsername());

        availabilities.sort(Comparator.comparing(a -> DayOfWeek.valueOf(a.getWeekDay().toUpperCase())));

        return availabilities;
    }

    @Operation(summary = "Get availabilities of all workers", tags = {"worker"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = AvailabilityResponse.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = AvailabilityResponse.class)))})
    })
    @GetMapping("/availability")
    public List<AvailabilityResponse> getAvailabilities() {
        List<User> workers = userService.findUsersByType(ERole.ROLE_WORKER);
        List<AvailabilityResponse> availabilities = new ArrayList<>();

        for (User worker : workers)
            for (Availability availability : ((Worker) worker).getAvailabilities()) {
                AvailabilityResponse availabilityResponse = new AvailabilityResponse();
                availabilityResponse.setWorkerId(worker.getId());
                availabilityResponse.setEndTime(availability.getEndTime());
                availabilityResponse.setStartTime(availability.getStartTime());
                availabilityResponse.setWeekDay(availability.getWeekDay());
                availabilityResponse.setId(availability.getId());

                availabilities.add(availabilityResponse);
            }

        return availabilities;
    }

    @Operation(summary = "Get upcoming bookings assigned to a worker", tags = {"worker"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Booking.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = Booking.class)))
            }),
            @ApiResponse(responseCode = "404", description = "Worker not found", content = @Content)
    })
    @GetMapping("/{workerId}/bookings")
    public List<Booking> getAssignedBookings(@Parameter(description = "Worker Id") @PathVariable Long workerId) {
        User user = userService.findById(workerId);
        return bookingService.findUpcomingBookingsByWorker(LocalDateTime.now(), user.getId());
    }

}

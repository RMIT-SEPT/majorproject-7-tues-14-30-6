package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.UpdateUserRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.MessageResponse;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "user", description = "the user API")

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)), @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
    })
    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Get user by id", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<?> getUserById(@Parameter(description = "The id of the user that needs to be fetched")
                                         @RequestParam(value = "id", required = false) Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get user by username", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    @GetMapping(value = "/find", params = "username")
    public ResponseEntity<?> getUserByUsername(@Parameter(description = "The username of the user that needs to be fetched")
                                               @RequestParam(value = "username", required = false) String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @Operation(summary = "Get user by email", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    @GetMapping(value = "/find", params = "email")
    public ResponseEntity<?> getUserByEmail(@Parameter(description = "The email of the user that needs to be fetched")
                                            @RequestParam(value = "email", required = false) String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Update user details", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Email is already taken")})
    @PutMapping(value = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable Long id) {
        User user = null;
        User emailMatch = null;
        try {
            user = userService.findById(id);
            emailMatch = userService.findByEmail(updateUserRequest.getEmail());
        } catch (UserNotFoundException userNotFoundException) {
            logger.info(userNotFoundException.getMessage());
        }

        if (emailMatch != null && !emailMatch.getId().equals(id))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));

        if (user != null) {
            user.setEmail(updateUserRequest.getEmail());
            user.setName(updateUserRequest.getName());
            user.setAddress(updateUserRequest.getAddress());
            user.setPhoneNumber(updateUserRequest.getPhoneNumber());
        }

        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }
}
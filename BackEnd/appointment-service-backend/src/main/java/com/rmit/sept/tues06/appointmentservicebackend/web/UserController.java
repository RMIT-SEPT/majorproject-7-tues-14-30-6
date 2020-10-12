package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.UpdateUserRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.MessageResponse;
import com.rmit.sept.tues06.appointmentservicebackend.security.jwt.JwtUtils;
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
import org.springframework.web.bind.annotation.*;

@Tag(name = "User")

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Get all users", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = User.class))),
                    @Content(mediaType = "application/xml", array = @ArraySchema(schema = @Schema(implementation = User.class)))}),
    })
    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @Operation(summary = "Get user", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping(value = "/find", params = "id")
    public ResponseEntity<?> getUserById(@Parameter(description = "The id of the user that needs to be fetched")
                                         @RequestParam(value = "id", required = false) Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get user", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping(value = "/find", params = "username")
    public ResponseEntity<?> getUserByUsername(@Parameter(description = "The username of the user that needs to be fetched")
                                               @RequestParam(value = "username", required = false) String username) {
        return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
    }

    @Operation(summary = "Get user", tags = {"user"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping(value = "/find", params = "email")
    public ResponseEntity<?> getUserByEmail(@Parameter(description = "The email of the user that needs to be fetched")
                                            @RequestParam(value = "email", required = false) String email) {
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @Operation(summary = "Update user details", description = "This can only be done by an authenticated user", tags = {"user"}, security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class)),
                    @Content(mediaType = "application/xml", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Email is already taken or logged in user is not the same as user to be updated", content = @Content)
    })
    @PutMapping(value = "/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@Parameter(description = "Access Token") @RequestHeader(value = "Authorization") String accessToken, @RequestBody UpdateUserRequest updateUserRequest,
                                        @Parameter(description = "User Id") @PathVariable Long id) {
        User user = null;
        try {
            user = userService.findById(id);
        } catch (UserNotFoundException userNotFoundException) {
            logger.info(userNotFoundException.getMessage());
        }

        String loggedInUsername = jwtUtils.getUserNameFromJwtToken(accessToken.substring(accessToken.indexOf(" ")));
        if (user != null && !user.getUsername().equals(loggedInUsername))
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: You are not allowed to change another user's details!"));

        User emailMatch = null;
        try {
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
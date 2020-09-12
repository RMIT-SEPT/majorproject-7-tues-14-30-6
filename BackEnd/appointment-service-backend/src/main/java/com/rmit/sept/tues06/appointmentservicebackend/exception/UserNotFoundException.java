package com.rmit.sept.tues06.appointmentservicebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String username) {
        super("User", "username", username);
    }
}

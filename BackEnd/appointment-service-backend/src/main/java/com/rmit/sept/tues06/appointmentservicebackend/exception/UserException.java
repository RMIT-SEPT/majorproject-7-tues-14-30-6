package com.rmit.sept.tues06.appointmentservicebackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }
}
package com.rmit.sept.tues06.appointmentservicebackend.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    @Schema(example = "ROLE_CUSTOMER successfully registered.")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.rmit.sept.tues06.appointmentservicebackend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceType, String keyName, String keyValue) {
        super(resourceType + " with " + keyName + " '" + keyValue + "' was not found");
    }
}

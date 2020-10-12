package com.rmit.sept.tues06.appointmentservicebackend.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdateUserRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Name is required")
    private String name;
    @Email
    @Size(max = 50)
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Schema(example = "customer", description = "username of the user")
    public String getUsername() {
        return username;
    }

    @Schema(example = "John Smith", description = "name of the user")
    public String getName() {
        return name;
    }

    @Schema(example = "johnsmith@email.com", description = "email of the user")
    public String getEmail() {
        return email;
    }

    @Schema(example = "123 ABC Street", description = "address of the user")
    public String getAddress() {
        return address;
    }

    @Schema(example = "0412345678", description = "phone number of the user")
    public String getPhoneNumber() {
        return phoneNumber;
    }
}

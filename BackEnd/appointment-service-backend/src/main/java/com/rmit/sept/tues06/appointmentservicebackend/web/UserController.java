package com.rmit.sept.tues06.appointmentservicebackend.web;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.service.CustomerService;
import com.rmit.sept.tues06.appointmentservicebackend.service.MapValidationErrorService;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("")
    public Iterable<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable(value = "username") String username) throws UserException {
        User user = userService.findByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable(value = "email") String email) throws UserException {
        User user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/add/customer")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if (errorMap != null) return errorMap;

        User newUser = userService.saveOrUpdateUser(customer);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
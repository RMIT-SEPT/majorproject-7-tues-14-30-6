package com.rmit.sept.tues06.apppointmentservicebackend.web;

import com.rmit.sept.tues06.apppointmentservicebackend.exception.CustomerException;
import com.rmit.sept.tues06.apppointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.apppointmentservicebackend.service.CustomerService;
import com.rmit.sept.tues06.apppointmentservicebackend.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// mark this class as a controller to handle /customer request
@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("")
    public Iterable<Customer> getAllCustomers() {
        return customerService.findAllCustomers();
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getCustomerByUsername(@PathVariable(value = "username") String username) throws CustomerException {
        Customer customer = customerService.findByUsername(username);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if (errorMap != null) return errorMap;

        Customer project1 = customerService.saveOrUpdatePerson(customer);
        return new ResponseEntity<>(project1, HttpStatus.CREATED);
    }

    // REFERENCES:
    // https://www.freecodecamp.org/news/how-to-build-a-rest-api-with-spring-boot-using-mysql-and-jpa-f931e348734b/
    // https://medium.com/swlh/build-deploy-a-rest-api-from-scratch-using-spring-boot-and-aws-ecs-eb369137a020
}
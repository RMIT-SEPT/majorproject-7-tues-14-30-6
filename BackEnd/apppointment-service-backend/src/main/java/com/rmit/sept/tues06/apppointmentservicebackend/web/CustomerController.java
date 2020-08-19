package com.rmit.sept.tues06.apppointmentservicebackend.web;
import com.rmit.sept.tues06.apppointmentservicebackend.exception.CustomerNotFoundException;
import com.rmit.sept.tues06.apppointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.apppointmentservicebackend.repository.CustomerRepository;
import com.rmit.sept.tues06.apppointmentservicebackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

// mark this class as a controller to handle /customer requests
@RestController
@RequestMapping(value = "/api/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;


    @GetMapping("")
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customers.add(customer);
        }

        return customers;
    }

    @GetMapping(value = "/{id}")
    public Customer getCustomer(@PathVariable(value = "id") Long userId) {
        return customerRepository.findById(userId).orElseThrow(() -> new CustomerNotFoundException(userId);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        if (result.hasErrors()){
            return new ResponseEntity<>("Invalid Customer Object", HttpStatus.BAD_REQUEST);
        }
        Customer newCustomer = customerService.saveOrUpdatePerson(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    // REFERENCES:
    // https://www.freecodecamp.org/news/how-to-build-a-rest-api-with-spring-boot-using-mysql-and-jpa-f931e348734b/
    // https://medium.com/swlh/build-deploy-a-rest-api-from-scratch-using-spring-boot-and-aws-ecs-eb369137a020

}
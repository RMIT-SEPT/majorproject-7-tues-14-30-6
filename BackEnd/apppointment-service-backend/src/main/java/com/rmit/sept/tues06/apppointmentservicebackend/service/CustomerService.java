package com.rmit.sept.tues06.apppointmentservicebackend.service;

import com.rmit.sept.tues06.apppointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.apppointmentservicebackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveOrUpdatePerson(Customer person) {
        //logic
        return customerRepository.save(person);
    }
}
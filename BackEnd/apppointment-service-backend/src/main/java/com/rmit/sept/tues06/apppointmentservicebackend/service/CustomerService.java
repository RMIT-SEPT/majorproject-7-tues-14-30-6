package com.rmit.sept.tues06.apppointmentservicebackend.service;

import com.rmit.sept.tues06.apppointmentservicebackend.exception.CustomerException;
import com.rmit.sept.tues06.apppointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.apppointmentservicebackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveOrUpdatePerson(Customer customer) {
        try {
            customer.setUsername(customer.getUsername().toUpperCase());
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new CustomerException("Customer '" + customer.getUsername().toUpperCase() + "' already exists");
        }
    }

    public Iterable<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username.toUpperCase());

        if (customer == null)
            throw new CustomerException("Customer '" + username + "' does not exist");

        return customer;
    }
}
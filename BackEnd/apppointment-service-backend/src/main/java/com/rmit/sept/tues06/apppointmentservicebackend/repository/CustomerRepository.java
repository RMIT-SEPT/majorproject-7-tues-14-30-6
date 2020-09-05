package com.rmit.sept.tues06.apppointmentservicebackend.repository;

import com.rmit.sept.tues06.apppointmentservicebackend.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUsername(String username);

    @Override
    Iterable<Customer> findAllById(Iterable<Long> iterable);

}
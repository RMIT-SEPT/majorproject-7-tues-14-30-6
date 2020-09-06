package com.rmit.sept.tues06.appointmentservicebackend.repository;

import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends UserBaseRepository<Customer> {
}
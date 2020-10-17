package com.rmit.sept.tues06.appointmentservicebackend.repository;

import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends UserBaseRepository<User> {
    List<User> findByIdIn(Set<Integer> idList);
}
package com.rmit.sept.tues06.appointmentservicebackend.repository;

import com.rmit.sept.tues06.appointmentservicebackend.model.User;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User> {
}
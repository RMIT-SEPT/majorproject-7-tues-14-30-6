package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.User;

public interface UserService {
    Iterable<User> findAllUsers();

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user);

    User updateUser(Long id, User user);
}

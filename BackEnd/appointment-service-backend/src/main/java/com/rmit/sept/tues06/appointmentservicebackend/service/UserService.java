package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.model.ERole;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    Iterable<User> findAllUsers();

    List<User> findUsersById(Set<Integer> idList);

    List<User> findUsersByType(ERole role);

    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user);

    User updateUser(Long id, User user);
}

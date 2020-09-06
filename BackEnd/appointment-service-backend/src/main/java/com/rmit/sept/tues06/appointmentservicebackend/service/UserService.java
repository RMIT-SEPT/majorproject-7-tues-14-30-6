package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserException;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase());
    }

    public User saveOrUpdateUser(User user) {
        try {
            user.setUsername(user.getUsername().toLowerCase());
            user.setEmail(user.getEmail());
            user.setName(user.getName());
            user.setPhoneNumber(user.getPhoneNumber());
            user.setRoles(user.getRoles());
            user.setAddress(user.getAddress());

            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserException("User '" + user.getUsername().toLowerCase() + "' already exists");
        }
    }

}
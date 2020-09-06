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

    public Boolean existsByUsername(String username) {
        return findByUsername(username)!= null;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username.toLowerCase());

        if (user == null)
            throw new UserException("User '" + username + "' does not exist");

        return user;
    }

    public Boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email.toLowerCase());

        if (user == null)
            throw new UserException("User with email '" + email + "' does not exist");

        return user;
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
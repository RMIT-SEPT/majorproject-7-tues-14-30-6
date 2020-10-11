package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserException;
import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("id", id + ""));
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsernameIgnoreCase(username.toLowerCase());

        if (user == null)
            throw new UserNotFoundException("username", username);

        return user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmailIgnoreCase(email.toLowerCase());

        if (user == null)
            throw new UserNotFoundException("email", email);

        return user;
    }

    @Override
    public User createUser(User user) {
        try {
            user.setUsername(user.getUsername().toLowerCase());
            user.setEmail(user.getEmail().toLowerCase());
            user.setName(user.getName());
            user.setPhoneNumber(user.getPhoneNumber());
            user.setRoles(user.getRoles());
            user.setAddress(user.getAddress());

            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserException("User '" + user.getUsername().toLowerCase() + "' already exists");
        }
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent())
            throw new UserNotFoundException("id", id + "");

        return userRepository.save(user);
    }
}
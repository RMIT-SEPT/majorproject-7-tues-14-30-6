package com.rmit.sept.tues06.appointmentservicebackend.service;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserNotFoundException;
import com.rmit.sept.tues06.appointmentservicebackend.model.ERole;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersById(Set<Integer> idList) {
        return userRepository.findByIdIn(idList);
    }

    @Override
    public List<User> findUsersByType(ERole role) {
        List<User> users = new ArrayList<>();
        Iterable<User> allUsers = userRepository.findAll();
        for (User u : allUsers)
            if (u.getRoles().iterator().next().getName() == role)
                users.add(u);

        return users;
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
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent())
            throw new UserNotFoundException("id", id + "");

        return userRepository.save(user);
    }
}
package com.rmit.sept.tues06.appointmentservicebackend.web;


import com.rmit.sept.tues06.appointmentservicebackend.model.ERole;
import com.rmit.sept.tues06.appointmentservicebackend.model.Role;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateWorkerRequest;
import com.rmit.sept.tues06.appointmentservicebackend.payload.response.MessageResponse;
import com.rmit.sept.tues06.appointmentservicebackend.repository.RoleRepository;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/add")
    public ResponseEntity<?> createWorker(@Valid @RequestBody CreateWorkerRequest createWorkerRequest) {
        if (userService.findByUsername(createWorkerRequest.getUsername()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.findByEmail(createWorkerRequest.getEmail()) != null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User worker = new Worker(createWorkerRequest.getUsername(), createWorkerRequest.getEmail(), encoder.encode(createWorkerRequest.getPassword()), createWorkerRequest.getName(),
                createWorkerRequest.getAddress(), createWorkerRequest.getPhoneNumber(), createWorkerRequest.getAvailabilities());
        Set<Role> roles = new HashSet<>();

        Role workerRole = roleRepository.findTopByName(ERole.ROLE_WORKER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(workerRole);
        worker.setRoles(roles);

        return new ResponseEntity<>(userService.createUser(worker), HttpStatus.CREATED);
    }

}

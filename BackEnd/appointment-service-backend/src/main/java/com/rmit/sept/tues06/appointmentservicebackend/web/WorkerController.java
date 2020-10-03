package com.rmit.sept.tues06.appointmentservicebackend.web;


import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;
import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateWorkerRequest;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> createWorker(@Valid @RequestBody CreateWorkerRequest createWorkerRequest) {
        User worker = new Worker(createWorkerRequest.getUsername(), createWorkerRequest.getEmail(), createWorkerRequest.getPassword(), createWorkerRequest.getName(),
                createWorkerRequest.getAddress(), createWorkerRequest.getPhoneNumber(), createWorkerRequest.getAvailabilities());

        return new ResponseEntity<>(userService.createUser(worker), HttpStatus.CREATED);
    }

}

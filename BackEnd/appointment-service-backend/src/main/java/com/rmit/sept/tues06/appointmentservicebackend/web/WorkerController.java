package com.rmit.sept.tues06.appointmentservicebackend.web;


import com.rmit.sept.tues06.appointmentservicebackend.payload.request.CreateWorkerRequest;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/workers")
public class WorkerController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createWorker(@Valid @RequestBody CreateWorkerRequest createWorkerRequest) {

    }

}

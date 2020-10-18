package com.rmit.sept.tues06.appointmentservicebackend;

import com.rmit.sept.tues06.appointmentservicebackend.model.Admin;
import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder encoder;

    @Autowired
    public DataLoader(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    public void run(ApplicationArguments args) {
        if (userService.findAllUsers().spliterator().getExactSizeIfKnown() == 0) {
            User customer1 = new Customer("customer", "customer1@email.com", encoder.encode("password"), "Jane D.", "123 Swanston Street", "0444984847");
            User customer2 = new Customer("customer2", "customer2@email.com", encoder.encode("password"), "John S.", "456 Swanston Street", "0422324847");
            User worker1 = new Worker("worker", "worker@email.com", encoder.encode("password"), "James R.", "9 Bourke Street", "0422368916");
            User worker2 = new Worker("worker2", "worker2@email.com", encoder.encode("password"), "Eliza M.", "4 Akuna Avenue", "041212121");
            User admin = new Admin("admin", "admin@email.com", encoder.encode("password"), "Valerie N.", "9 Spencer Street", "041212121");

            userService.createUser(customer1);
            userService.createUser(customer2);
            userService.createUser(worker1);
            userService.createUser(worker2);
            userService.createUser(admin);
        }
    }
}
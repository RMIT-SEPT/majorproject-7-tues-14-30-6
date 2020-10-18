package com.rmit.sept.tues06.appointmentservicebackend;

import com.rmit.sept.tues06.appointmentservicebackend.model.Admin;
import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.model.Worker;
import com.rmit.sept.tues06.appointmentservicebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        if (userRepository.findAll().spliterator().getExactSizeIfKnown() == 0) {
            User customer1 = new Customer("customer", "customer1@email.com", "password", "Jane D.", "123 Swanston Street", "0444984847");
            User customer2 = new Customer("customer2", "customer2@email.com", "password", "John S.", "456 Swanston Street", "0422324847");
            User worker1 = new Worker("worker", "worker@email.com", "password", "James R.", "9 Bourke Street", "0422368916");
            User worker2 = new Worker("worker2", "worker2@email.com", "password", "Eliza M.", "4 Akuna Avenue", "041212121");
            User admin = new Admin("admin", "admin@email.com", "password", "Valerie N.", "9 Spencer Street", "041212121");

            userRepository.save(customer1);
            userRepository.save(customer2);
            userRepository.save(worker1);
            userRepository.save(worker2);
            userRepository.save(admin);
        }
    }
}
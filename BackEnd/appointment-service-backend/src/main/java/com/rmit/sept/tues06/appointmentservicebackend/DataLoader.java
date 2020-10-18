package com.rmit.sept.tues06.appointmentservicebackend;

import com.rmit.sept.tues06.appointmentservicebackend.model.*;
import com.rmit.sept.tues06.appointmentservicebackend.repository.RoleRepository;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader {
    private static int counter;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void init() {
        // if there are no users currently
        if (userService.findAllUsers().spliterator().getExactSizeIfKnown() == 0) {
            counter = 0;

            while (counter < 5) {
                Set<Role> roles = new HashSet<>();

                Role customerRole1 = new Role();
                Role customerRole2 = new Role();
                customerRole1.setName(ERole.ROLE_CUSTOMER);
                customerRole2.setName(ERole.ROLE_CUSTOMER);

                Role workerRole1 = new Role();
                Role workerRole2 = new Role();
                workerRole1.setName(ERole.ROLE_WORKER);
                workerRole2.setName(ERole.ROLE_WORKER);

                Role adminRole = new Role();
                adminRole.setName(ERole.ROLE_ADMIN);

                roles.add(customerRole1);
                User customer1 = new Customer("customer", "customer1@email.com", encoder.encode("password"), "Jane D.", "123 Swanston Street", "0444984847");
                customer1.setRoles(roles);

                roles.clear();
                roles.add(customerRole2);
                User customer2 = new Customer("customer2", "customer2@email.com", encoder.encode("password"), "John S.", "456 Swanston Street", "0422324847");
                customer2.setRoles(roles);

                roles.clear();
                roles.add(workerRole1);
                User worker1 = new Worker("worker", "worker@email.com", encoder.encode("password"), "James R.", "9 Bourke Street", "0422368916");
                worker1.setRoles(roles);

                roles.clear();
                roles.add(workerRole2);
                User worker2 = new Worker("worker2", "worker2@email.com", encoder.encode("password"), "Eliza M.", "4 Akuna Avenue", "041212121");
                worker2.setRoles(roles);

                roles.clear();
                roles.add(adminRole);
                User admin = new Admin("admin", "admin@email.com", encoder.encode("password"), "Valerie N.", "9 Spencer Street", "041212121");
                admin.setRoles(roles);

                if (counter == 0) {
                    userService.createUser(customer1);
                } else if (counter == 1) {
                    userService.createUser(customer2);
                } else if (counter == 2) {
                    userService.createUser(worker1);
                } else if (counter == 3) {
                    userService.createUser(worker2);
                } else {
                    userService.createUser(admin);
                }

                ++counter;
            }
        }
    }
}
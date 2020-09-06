package com.rmit.sept.tues06.appointmentservicebackend;


import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepositoryIntegrationTest {
    @Resource
    private UserService userService;

    @Before
    public void setup() {
        User customer1 = new Customer("customer1", "customer1@email.com", "Customer One", "123 ABC Street", "0400000000");
        userService.saveOrUpdateUser(customer1);
        User customer2 = new Customer("customer2", "customer2@email.com", "Customer Two", "123 ABC Street", "0400000000");
        userService.saveOrUpdateUser(customer2);
        User customer3 = new Customer("customer3", "customer3@email.com", "Customer Three", "123 ABC Street", "0400000000");
        userService.saveOrUpdateUser(customer3);
    }

    @Test
    public void givenCustomer_whenFindByEmail_thenOk() {
        User customer = userService.findByUsername("customer1@email.com");
        assert (customer.getEmail().equals("customer1@email.com"));
    }
}

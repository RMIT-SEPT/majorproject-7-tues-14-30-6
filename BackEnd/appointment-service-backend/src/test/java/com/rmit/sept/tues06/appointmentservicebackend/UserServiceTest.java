package com.rmit.sept.tues06.appointmentservicebackend;

import com.rmit.sept.tues06.appointmentservicebackend.exception.UserException;
import com.rmit.sept.tues06.appointmentservicebackend.model.Customer;
import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserService;
import com.rmit.sept.tues06.appointmentservicebackend.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {
    @Autowired
    private final UserService userService = new UserServiceImpl();

    @Test
    public void When_NoUsers_FindAllUsers_Empty() {
        Iterable<User> users = userService.findAllUsers();
        List<User> userList = new ArrayList<User>();
        users.forEach(userList::add);

        Assertions.assertEquals(userList.size(), 0);
    }

    @Test
    public void When_NoPassword_Expect_UserException() {
        User user = new Customer("test1","test@test.com","","test","test test","1");
        Assertions.assertThrows(UserException.class,()->userService.createUser(user),"Password is required");
    }

    @Test
    public void When_ShortPassword_Expect_UserException() {
        User user = new Customer("test2","test@test.com","12345","test","test test","1");
        Assertions.assertThrows(UserException.class,()->userService.createUser(user));
    }

    @Test
    public void When_MinPassword_Expect_Success() {
        User customer = new Customer("test3","test@test.com","123456","test","test test","1");
        User user = userService.createUser(customer);
        Assertions.assertNotNull(user);
    }

    @Test void When_LongPassword_Expect_UserException() {
        String password = new String(new char[121]).replace('\0', 'a');
        User user = new Customer("test4","test@test.com",password,"test","test test","1");
        Assertions.assertThrows(UserException.class,()->userService.createUser(user));
    }
}

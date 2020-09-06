package com.rmit.sept.tues06.appointmentservicebackend.repository;

import com.rmit.sept.tues06.appointmentservicebackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends User>
        extends CrudRepository<T, Long> {

    /**
     * Method findByEmail
     *
     * @param email the user email.
     * @return the user having the passed email or null if no user is found.
     */
    T findByEmail(String email);

    T findByUsername(String username);
}
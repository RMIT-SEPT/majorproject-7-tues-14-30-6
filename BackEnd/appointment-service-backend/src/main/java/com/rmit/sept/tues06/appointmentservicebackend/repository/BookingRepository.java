package com.rmit.sept.tues06.appointmentservicebackend.repository;

import com.rmit.sept.tues06.appointmentservicebackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b where b.isActive = TRUE and b.bookingDateTime < :bookingDateTime ORDER BY b.bookingDateTime DESC")
    List<Booking> findActivePastBookings(@Param("bookingDateTime") Date bookingDateTime);

    @Query("select b from Booking b where b.isActive = TRUE and b.bookingDateTime >= :bookingDateTime ORDER BY b.bookingDateTime DESC")
    List<Booking> findActiveCurrentBookings(@Param("bookingDateTime") Date bookingDateTime);

    @Query("select b from Booking b where b.isActive = TRUE and b.bookingDateTime < :bookingDateTime and b.customer.username = :username ORDER BY b.bookingDateTime DESC")
    List<Booking> findActivePastBookingsByCustomer(@Param("bookingDateTime") Date bookingDateTime, @Param("username") String username);

    @Query("select b from Booking b where b.isActive = TRUE and b.bookingDateTime >= :bookingDateTime and b.customer.username = :username ORDER BY b.bookingDateTime DESC")
    List<Booking> findActiveCurrentBookingsByCustomer(@Param("bookingDateTime") Date bookingDateTime, @Param("username") String username);
}

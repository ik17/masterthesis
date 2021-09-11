package com.bookingservice.customerservice.repository;

import com.bookingservice.customerservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT * FROM customer WHERE user_identifier = :userIdentifier LIMIT 1", nativeQuery = true)
    Customer findByUserIdentifier(@Param("userIdentifier") String userIdentifier);
}

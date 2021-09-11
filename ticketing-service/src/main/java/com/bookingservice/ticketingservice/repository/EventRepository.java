package com.bookingservice.ticketingservice.repository;

import com.bookingservice.ticketingservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

}

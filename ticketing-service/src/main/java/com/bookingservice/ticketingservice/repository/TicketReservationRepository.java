package com.bookingservice.ticketingservice.repository;

import com.bookingservice.ticketingservice.model.Event;
import com.bookingservice.ticketingservice.model.TicketReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketReservationRepository extends JpaRepository<TicketReservation, Integer> {

    List<TicketReservation> findAllByEvent(Event e);
    List<TicketReservation> findAllByUserIdentification(Integer userId);
}

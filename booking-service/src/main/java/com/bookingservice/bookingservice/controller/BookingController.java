package com.bookingservice.bookingservice.controller;

import com.bookingservice.bookingservice.entity.Event;
import com.bookingservice.bookingservice.entity.Ticket;
import com.bookingservice.bookingservice.exception.BusinessException;
import com.bookingservice.bookingservice.service.CustomerService;
import com.bookingservice.bookingservice.service.TicketingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/booking")
public class BookingController {

    @Autowired
    CustomerService customerService;

    @Autowired
    TicketingService ticketingService;

    @GetMapping(value = "/{id}")
    public List<Ticket> getAllCustomersTickets(@PathVariable("id") String userIdentifier) throws JsonProcessingException {
        Integer customerId = customerService.getUserId(userIdentifier);
        return ticketingService.getUserTickets(customerId);
    }

    @PostMapping(value = "/bookticket/")
    public ResponseEntity<String> bookTicket(@RequestBody Event event) throws JsonProcessingException {
        Integer customerId = customerService.getUserId(event.getUserIdentifier());
        return ticketingService.makeReservation(customerId, event.getEventId(), event.getNumberOfTickets());
    }

    @DeleteMapping(value = "/deleteticket/{id}")
    public boolean deleteReservation(@RequestBody String userIdentifier, @PathVariable("id") Integer ticketReservationId) throws JsonProcessingException, BusinessException {
        Integer customerId = customerService.getUserId(userIdentifier);
        if (customerId == null) throw new BusinessException("Invalid user identifier!");
        return ticketingService.deleteTicket(ticketReservationId);
    }
}

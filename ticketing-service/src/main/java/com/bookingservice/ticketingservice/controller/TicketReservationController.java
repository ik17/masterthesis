package com.bookingservice.ticketingservice.controller;

import com.bookingservice.ticketingservice.exception.BusinessException;
import com.bookingservice.ticketingservice.model.TicketReservation;
import com.bookingservice.ticketingservice.service.TicketReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ticketreservation")
public class TicketReservationController {

    @Autowired
    private TicketReservationService ticketReservationService;

    @GetMapping(value = "/{id}")
    public TicketReservation getTicketReservationtById(@PathVariable("id") Integer id) throws BusinessException {
        return ticketReservationService.getById(id);
    }

    @GetMapping("/")
    public List<TicketReservation> getAllTicketReservations() {
        return ticketReservationService.getAllTicketReservations();
    }

    @GetMapping(value = "/customer/{id}")
    public List<TicketReservation> getTicketsOfUser(@PathVariable("id") Integer id) throws BusinessException {
        return ticketReservationService.getCustomerTickets(id);
    }

    @PostMapping("/")
    public TicketReservation createTicketReservation(@RequestBody TicketReservation ticketReservation) throws BusinessException {
        return ticketReservationService.createTicketReservation(ticketReservation);
    }

    @PutMapping("/")
    public TicketReservation updateTicketReservation(@RequestBody TicketReservation ticketReservation) throws BusinessException {
        return ticketReservationService.updateTicketReservation(ticketReservation);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteTicketReservationt(@PathVariable("id") Integer id) throws BusinessException {
        return ticketReservationService.deleteTicketReservation(id);
    }

}

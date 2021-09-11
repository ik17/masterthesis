package com.bookingservice.ticketingservice.service;

import com.bookingservice.ticketingservice.exception.BusinessException;
import com.bookingservice.ticketingservice.model.Event;
import com.bookingservice.ticketingservice.model.TicketReservation;
import com.bookingservice.ticketingservice.repository.TicketReservationRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TicketReservationService {

    @Autowired
    TicketReservationRepository ticketReservationRepository;

    @Autowired
    EventService eventService;

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public TicketReservation getById(Integer id) throws BusinessException {
        TicketReservation ticketReservation = ticketReservationRepository.findById(id).orElse(null);
        if (ticketReservation == null) throw new BusinessException("Ticket reservation with given id doesn't exist!");
        return ticketReservation;
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public List<TicketReservation> getAllTicketReservations() {
        return ticketReservationRepository.findAll();
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public TicketReservation createTicketReservation(TicketReservation ticketReservation) throws BusinessException {
        Event e = eventService.getById(ticketReservation.getEvent().getEventId());
        if (ticketReservation.getNumberOfTickets() <= 0 || ticketReservation.getEvent() == null ||
        ticketReservation.getUserIdentification() == null || ticketReservation.getTicketReservationId() != null ||
                e.getVenueCapacity() - e.getReservedTickets() < ticketReservation.getNumberOfTickets()) throw new BusinessException("Ticket reservation parameters not valid!");
        e.setReservedTickets(e.getReservedTickets() + ticketReservation.getNumberOfTickets());
        e = eventService.updateEvent(e);
        ticketReservation.setEvent(e);
        return ticketReservationRepository.save(ticketReservation);
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public TicketReservation updateTicketReservation(TicketReservation ticketReservation) throws BusinessException {
        TicketReservation ticketReservation1 = ticketReservationRepository.findById(ticketReservation.getTicketReservationId()).orElse(null);
        if (ticketReservation1 == null) throw new BusinessException("Ticket reservation with given id doesn't exist!");

        Event e = eventService.getById(ticketReservation.getEvent().getEventId());
        e.setReservedTickets(e.getReservedTickets() - ticketReservation1.getNumberOfTickets() + ticketReservation.getNumberOfTickets());
        if (e.getReservedTickets() > e.getVenueCapacity()) throw new BusinessException("Not enough tickets available!");
        e = eventService.updateEvent(e);
        ticketReservation.setEvent(e);
        return ticketReservationRepository.save(ticketReservation);
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public boolean deleteTicketReservation(Integer id) throws BusinessException {
        TicketReservation ticketReservation = ticketReservationRepository.findById(id).get();
        if (ticketReservation == null) throw new BusinessException("Ticket reservation with given id doesn't exist!");
        Event e = ticketReservation.getEvent();
        e.setReservedTickets(e.getReservedTickets() - ticketReservation.getNumberOfTickets());
        ticketReservationRepository.delete(ticketReservation);
        eventService.updateEvent(e);
        return true;
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public List<TicketReservation> getCustomerTickets(Integer customerId) {
        return ticketReservationRepository.findAllByUserIdentification(customerId);
    }
}

package com.bookingservice.ticketingservice.service;

import com.bookingservice.ticketingservice.exception.BusinessException;
import com.bookingservice.ticketingservice.model.Event;
import com.bookingservice.ticketingservice.model.TicketReservation;
import com.bookingservice.ticketingservice.repository.EventRepository;
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
public class EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    TicketReservationRepository ticketReservationRepository;

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public Event getById(Integer id) throws BusinessException {
        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) throw new BusinessException("Event with given id doesn't exist!");
        return event;
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public Event createEvent(Event event) throws BusinessException {
        if (event.getVenueCapacity() <= 0) throw new BusinessException("Venue capacity is not valid!");
        event.setReservedTickets(0);
        return eventRepository.save(event);
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public Event updateEvent(Event event) throws BusinessException {
        Event event1 = eventRepository.findById(event.getEventId()).orElse(null);
        if (event1 == null) throw new BusinessException("Event with given id doesn't exist!");

        if (event.getVenueCapacity() <= 0 || event.getReservedTickets() > event.getVenueCapacity())
            throw new BusinessException("Venue size not valid!");

        return eventRepository.save(event);
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public Integer numberOfAvailableTickets(Integer id) {
        Event event = eventRepository.findById(id).get();
        return event.getVenueCapacity() - event.getReservedTickets();
    }

    @CircuitBreaker(name = "ticketingdb")
    @Bulkhead(name = "ticketingdb")
    @Retry(name = "ticketingdb")
    @RateLimiter(name = "ticketingdb")
    public boolean deleteEvent(Integer id) throws BusinessException {
        Event e = eventRepository.findById(id).get();
        if (e == null) throw new BusinessException("Event with given id doesn't exist!");
        deleteAllTicketsForEvent(e);
        eventRepository.delete(e);
        return true;
    }

    public boolean deleteAllTicketsForEvent(Event e) {
        List<TicketReservation> list = ticketReservationRepository.findAllByEvent(e);
        list.forEach(element -> ticketReservationRepository.delete(element));
        return true;
    }
}

package com.bookingservice.ticketingservice.controller;

import com.bookingservice.ticketingservice.exception.BusinessException;
import com.bookingservice.ticketingservice.model.Event;
import com.bookingservice.ticketingservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    EventService eventService;

    @GetMapping(value = "/{id}")
    public Event getEventById(@PathVariable("id") Integer id) throws BusinessException {
        return eventService.getById(id);
    }

    @GetMapping("/")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/")
    public Event createEvent(@RequestBody Event event) throws BusinessException {
        return eventService.createEvent(event);
    }

    @PutMapping("/")
    public Event updateEvent(@RequestBody Event event) throws BusinessException {
        return eventService.updateEvent(event);
    }

    @DeleteMapping(value = "/{id}")
    public boolean deleteEvent(@PathVariable("id") Integer id) throws BusinessException {
        return eventService.deleteEvent(id);
    }
}

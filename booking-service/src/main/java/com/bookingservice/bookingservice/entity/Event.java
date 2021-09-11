package com.bookingservice.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {

    String userIdentifier;
    Integer eventId;
    Integer numberOfTickets;
}

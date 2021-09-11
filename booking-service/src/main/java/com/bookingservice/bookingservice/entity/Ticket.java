package com.bookingservice.bookingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    private String ticketReservationId;
    private String eventId;
    private String eventName;
    private String numberOfTickets;
}

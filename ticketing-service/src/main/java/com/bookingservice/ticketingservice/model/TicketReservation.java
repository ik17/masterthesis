package com.bookingservice.ticketingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ticket_reservation")
public class TicketReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketReservationId;

    private Integer userIdentification;

    @ManyToOne
    @JoinColumn(name = "event_id")
    @Fetch(value= FetchMode.SELECT)
    private Event event;

    private Integer numberOfTickets;

    @PrePersist
    private void generateTicketIdentifier() {
        this.setTicketIdentifier(UUID.randomUUID().toString());
    }

    private String ticketIdentifier;
}

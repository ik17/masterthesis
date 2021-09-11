package com.bookingservice.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    private String firstName;

    private String lastName;

    @PrePersist
    private void generateUserIdentifier() {
        this.setUserIdentifier(UUID.randomUUID().toString());
    }

    @GeneratedValue(generator = "uuid-gen")
    private String userIdentifier;
}

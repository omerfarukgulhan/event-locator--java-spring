package com.ofg.event.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "registrations", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "event_id"})})
@Data
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private boolean attended;
}

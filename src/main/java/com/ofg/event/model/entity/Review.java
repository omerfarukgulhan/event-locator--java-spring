package com.ofg.event.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "event_id"})})
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int rating;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}

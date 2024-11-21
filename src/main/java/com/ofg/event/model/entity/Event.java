package com.ofg.event.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(name = "event_image", nullable = false)
    private String eventImage = "default.png";

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private int maxCapacity;

    @Column(nullable = false)
    private int currentRegistrations = 0;

    @Column(nullable = false)
    private int numberOfReviews = 0;

    @Column(nullable = false)
    private double averageRating = 0.0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}

package com.ofg.event.model.response;

import com.ofg.event.model.entity.Category;
import com.ofg.event.model.entity.Event;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String name,
        String description,
        String location,
        double latitude,
        double longitude,
        String eventImage,
        LocalDateTime dateTime,
        int maxCapacity,
        int currentRegistrations,
        int numberOfReviews,
        double averageRating,
        Category category
) {
    public EventResponse(Event event) {
        this(event.getId(), event.getName(),
                event.getDescription(), event.getLocation(),
                event.getLatitude(), event.getLongitude(),
                event.getEventImage(), event.getDateTime(),
                event.getMaxCapacity(), event.getCurrentRegistrations(),
                event.getNumberOfReviews(), event.getAverageRating(),
                event.getCategory());
    }
}

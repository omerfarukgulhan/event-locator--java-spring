package com.ofg.event.model.response;

import com.ofg.event.model.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long eventId,
        Long userId,
        String userName,
        int rating,
        String comment,
        LocalDateTime createdAt
) {
    public ReviewResponse(Review review) {
        this(review.getId(), review.getUser().getId(), review.getEvent().getId(),
                review.getUser().getFirstName() + " " + review.getUser().getLastName(),
                review.getRating(), review.getComment(), review.getCreatedAt());
    }
}

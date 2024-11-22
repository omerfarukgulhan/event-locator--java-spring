package com.ofg.event.model.request;

import com.ofg.event.model.entity.Event;
import com.ofg.event.model.entity.Review;
import jakarta.validation.constraints.*;

public record ReviewCreateRequest(
        @NotNull(message = "Event id cannot be null.")
        long eventId,
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        int rating,
        @Size(max = 500, message = "Comment must be under 500 characters")
        String comment
) {
    public Review toReview(Event event) {
        Review review = new Review();
        review.setEvent(event);
        review.setRating(rating);
        review.setComment(comment);
        return review;
    }
}


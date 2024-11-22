package com.ofg.event.service.abstracts;

import com.ofg.event.model.request.ReviewCreateRequest;
import com.ofg.event.model.request.ReviewUpdateRequest;
import com.ofg.event.model.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    Page<ReviewResponse> getAllReviewsForEvent(long eventId, Pageable pageable);

    ReviewResponse addReview(long userId, ReviewCreateRequest reviewCreateRequest);

    ReviewResponse updateReview(long userId, long reviewId, ReviewUpdateRequest reviewUpdateRequest);

    void deleteReview(long userId, long reviewId);
}

package com.ofg.event.service.concretes;

import com.ofg.event.exception.authentication.UnauthorizedException;
import com.ofg.event.exception.general.DuplicateOperationException;
import com.ofg.event.exception.general.NotFoundException;
import com.ofg.event.model.entity.Event;
import com.ofg.event.model.entity.Review;
import com.ofg.event.model.entity.User;
import com.ofg.event.model.request.EventUpdateRequest;
import com.ofg.event.model.request.ReviewCreateRequest;
import com.ofg.event.model.request.ReviewUpdateRequest;
import com.ofg.event.model.response.ReviewResponse;
import com.ofg.event.repository.ReviewRepository;
import com.ofg.event.service.abstracts.EventService;
import com.ofg.event.service.abstracts.ReviewService;
import com.ofg.event.service.abstracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             UserService userService,
                             EventService eventService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    public Page<ReviewResponse> getAllReviewsForEvent(long eventId, Pageable pageable) {
        return reviewRepository.findByEventId(eventId, pageable)
                .map(ReviewResponse::new);
    }

    @Override
    public ReviewResponse addReview(long userId, ReviewCreateRequest reviewCreateRequest) {
        User user = userService.getUserEntityById(userId);
        Event event = eventService.getEventEntityById(reviewCreateRequest.eventId());

        Review review = reviewCreateRequest.toReview(event);
        review.setUser(user);
        review.setCreatedAt(LocalDateTime.now());

        try {
            Review savedReview = reviewRepository.save(review);
            updateEventRatingAndReviewCount(event);
            return new ReviewResponse(savedReview);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateOperationException("User", "review", userId, reviewCreateRequest.eventId());
        }
    }

    @Override
    public ReviewResponse updateReview(long userId, long reviewId, ReviewUpdateRequest reviewUpdateRequest) {
        Review existingReview = getAndValidateReviewOwnership(reviewId, userId);
        updateReviewDetails(existingReview, reviewUpdateRequest);
        Review updatedReview = reviewRepository.save(existingReview);
        updateEventRatingAndReviewCount(existingReview.getEvent());
        return new ReviewResponse(updatedReview);
    }

    @Override
    public void deleteReview(long userId, long reviewId) {
        Review existingReview = getAndValidateReviewOwnership(reviewId, userId);
        Event event = existingReview.getEvent();
        reviewRepository.deleteById(reviewId);
        updateEventRatingAndReviewCount(event);
    }

    private Review getAndValidateReviewOwnership(long reviewId, long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundException(reviewId));
        if (review.getUser().getId() != userId) {
            throw new UnauthorizedException();
        }
        return review;
    }

    private void updateReviewDetails(Review review, ReviewUpdateRequest reviewUpdateRequest) {
        if (reviewUpdateRequest.comment() != null) {
            review.setRating(reviewUpdateRequest.rating());
            review.setComment(reviewUpdateRequest.comment());
            review.setCreatedAt(LocalDateTime.now());
        } else {
            review.setRating(reviewUpdateRequest.rating());
            review.setCreatedAt(LocalDateTime.now());
        }
    }

    private void updateEventRatingAndReviewCount(Event event) {
        long numberOfReviews = reviewRepository.countByEvent(event);
        double averageRating = reviewRepository.findByEvent(event).stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        event.setNumberOfReviews((int) numberOfReviews);
        event.setAverageRating(averageRating);
        eventService.updateEvent(event.getId(),
                new EventUpdateRequest(event.getName(), event.getDescription(),
                        event.getLocation(), event.getLatitude(),
                        event.getLongitude(), event.getEventImage(),
                        event.getDateTime(), event.getMaxCapacity(),
                        event.getCategory().getId()));
    }
}

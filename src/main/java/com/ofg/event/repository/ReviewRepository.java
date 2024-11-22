package com.ofg.event.repository;

import com.ofg.event.model.entity.Event;
import com.ofg.event.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByEventId(Long eventId, Pageable pageable);

    List<Review> findByEvent(Event event);

    long countByEvent(Event event);
}

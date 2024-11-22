package com.ofg.event.repository;

import com.ofg.event.model.entity.Category;
import com.ofg.event.model.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByCategory(Category category, Pageable pageable);

    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.category = :category AND e.name LIKE %:name%")
    Page<Event> findByCategoryAndNameContaining(Category category, String name, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.dateTime BETWEEN :startWindow AND :endWindow")
    List<Event> findEventsWithinNotificationWindow(
            @Param("startWindow") LocalDateTime startWindow,
            @Param("endWindow") LocalDateTime endWindow);
}
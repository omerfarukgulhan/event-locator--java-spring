package com.ofg.event.repository;

import com.ofg.event.model.entity.Registration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByUserIdAndEventId(Long userId, Long eventId);

    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId AND r.event.dateTime > :now")
    Page<Registration> findByUserIdAndEventDateAfter(Long userId, LocalDateTime now, Pageable pageable);

    @Query("SELECT r FROM Registration r WHERE r.user.id = :userId AND r.event.dateTime < :now")
    Page<Registration> findByUserIdAndEventDateBefore(Long userId, LocalDateTime now, Pageable pageable);
}

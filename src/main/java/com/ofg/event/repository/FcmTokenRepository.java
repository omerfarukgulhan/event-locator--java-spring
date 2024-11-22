package com.ofg.event.repository;

import com.ofg.event.model.entity.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> getByUserId(Long userId);
}

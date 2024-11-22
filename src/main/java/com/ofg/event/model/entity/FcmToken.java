package com.ofg.event.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fcm_tokens")
@Data
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String fcmToken;
}

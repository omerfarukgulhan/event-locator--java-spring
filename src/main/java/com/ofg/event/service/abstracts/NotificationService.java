package com.ofg.event.service.abstracts;

public interface NotificationService {
    void sendNotification(String fcmToken, String title, String body);
}

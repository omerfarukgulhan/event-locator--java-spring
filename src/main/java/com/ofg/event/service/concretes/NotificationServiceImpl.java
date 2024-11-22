package com.ofg.event.service.concretes;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.ofg.event.config.FirebaseInitializer;
import com.ofg.event.service.abstracts.NotificationService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    @PostConstruct
    public void initFirebase() {
        FirebaseInitializer.initializeFirebase();
    }

    @Override
    public void sendNotification(String fcmToken, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(fcmToken)
                    .putData("title", title)
                    .putData("body", body)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

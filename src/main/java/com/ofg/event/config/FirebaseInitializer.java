package com.ofg.event.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

public class FirebaseInitializer {
    public static void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


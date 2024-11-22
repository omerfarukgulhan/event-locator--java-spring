package com.ofg.event.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Component
public class FirebaseInitializer {
    @Value("${firebase.type}")
    private String type;
    @Value("${firebase.project-id}")
    private String projectId;
    @Value("${firebase.private-key-id}")
    private String privateKeyId;
    @Value("${firebase.private-key}")
    private String privateKey;
    @Value("${firebase.client-email}")
    private String clientEmail;
    @Value("${firebase.client-id}")
    private String clientId;
    @Value("${firebase.auth-uri}")
    private String authUri;
    @Value("${firebase.token-uri}")
    private String tokenUri;
    @Value("${firebase.auth-provider-cert-url}")
    private String authProviderCertUrl;
    @Value("${firebase.client-cert-url}")
    private String clientCertUrl;

    public void initializeFirebase() {
        try {
            String serviceAccountJson = String.format(
                    "{ \"type\": \"%s\", \"project_id\": \"%s\", \"private_key_id\": \"%s\", " +
                            "\"private_key\": \"%s\", \"client_email\": \"%s\", \"client_id\": \"%s\", " +
                            "\"auth_uri\": \"%s\", \"token_uri\": \"%s\", \"auth_provider_x509_cert_url\": \"%s\", " +
                            "\"client_x509_cert_url\": \"%s\" }",
                    type, projectId, privateKeyId, privateKey, clientEmail, clientId,
                    authUri, tokenUri, authProviderCertUrl, clientCertUrl
            );

            ByteArrayInputStream serviceAccountStream = new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8));
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase initialized successfully with properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



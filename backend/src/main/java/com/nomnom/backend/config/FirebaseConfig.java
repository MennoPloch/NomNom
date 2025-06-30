package com.nomnom.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Value("${FIREBASE_PROJECT_ID:}")
    private String projectId;

    @Value("${FIREBASE_API_KEY:}")
    private String apiKey;

    @Value("${FIREBASE_AUTH_DOMAIN:}")
    private String authDomain;

    @Value("${FIREBASE_STORAGE_BUCKET:}")
    private String storageBucket;

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty() && !projectId.isEmpty()) {
                // Create a simple service account JSON for Firebase Admin SDK
                // Note: For production, you should use a proper service account key file
                String serviceAccountJson = String.format(
                    "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"%s\",\n" +
                    "  \"private_key_id\": \"dummy\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nDUMMY_KEY\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk@%s.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"dummy\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\"\n" +
                    "}",
                    projectId, projectId
                );

                GoogleCredentials credentials = GoogleCredentials.fromStream(
                    new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8))
                );

                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setProjectId(projectId)
                    .setStorageBucket(storageBucket)
                    .build();

                FirebaseApp.initializeApp(options);
                System.out.println("Firebase initialized successfully for project: " + projectId);
            }
        } catch (IOException e) {
            System.err.println("Error initializing Firebase: " + e.getMessage());
            // For development purposes, we'll continue without Firebase
            // In production, you might want to fail the application startup
        }
    }
} 
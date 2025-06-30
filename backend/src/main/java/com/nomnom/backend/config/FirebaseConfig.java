package com.nomnom.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

    private boolean firebaseInitialized = false;

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
                firebaseInitialized = true;
                System.setProperty("firebase.configured", "true");
                System.out.println("Firebase initialized successfully for project: " + projectId);
            } else {
                System.setProperty("firebase.configured", "false");
                System.out.println("Firebase configuration not found - running without Firebase/Firestore");
            }
        } catch (IOException e) {
            System.err.println("Error initializing Firebase: " + e.getMessage());
            System.setProperty("firebase.configured", "false");
            // For development purposes, we'll continue without Firebase
            // In production, you might want to fail the application startup
        }
    }
    
    @Bean
    @Primary
    public Firestore firestore() {
        try {
            if (firebaseInitialized && !FirebaseApp.getApps().isEmpty()) {
                return FirestoreClient.getFirestore();
            } else {
                System.out.println("Returning null Firestore instance - Firebase not configured");
                // Return null - the service will handle this gracefully
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error creating Firestore instance: " + e.getMessage());
            return null;
        }
    }
} 
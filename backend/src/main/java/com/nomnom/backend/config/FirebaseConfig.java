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
            // Only initialize Firebase if we have valid configuration
            if (FirebaseApp.getApps().isEmpty() && !projectId.isEmpty() && 
                !projectId.equals("nomnom-f3371")) { // Skip test project ID
                
                // For proper Firebase setup, you need a real service account JSON file
                // This is just a placeholder - in real usage, load from environment or file
                System.out.println("Firebase project ID provided but no proper credentials configured");
                System.out.println("To use Firebase, provide proper service account credentials");
                System.setProperty("firebase.configured", "false");
            } else {
                System.setProperty("firebase.configured", "false");
                System.out.println("Firebase configuration not found - running without Firebase/Firestore");
            }
        } catch (Exception e) {
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
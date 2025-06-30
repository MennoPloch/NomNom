package com.nomnom.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        // Load .env file before application starts
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // Set environment variables from .env file
        if (dotenv != null) {

            // Firebase Configuration
            System.setProperty("FIREBASE_PROJECT_ID", dotenv.get("FIREBASE_PROJECT_ID", ""));
            System.setProperty("FIREBASE_API_KEY", dotenv.get("FIREBASE_API_KEY", ""));
            System.setProperty("FIREBASE_AUTH_DOMAIN", dotenv.get("FIREBASE_AUTH_DOMAIN", ""));
            System.setProperty("FIREBASE_STORAGE_BUCKET", dotenv.get("FIREBASE_STORAGE_BUCKET", ""));
            System.setProperty("FIREBASE_MESSAGING_SENDER_ID", dotenv.get("FIREBASE_MESSAGING_SENDER_ID", ""));
            System.setProperty("FIREBASE_APP_ID", dotenv.get("FIREBASE_APP_ID", ""));
            System.setProperty("FIREBASE_MEASUREMENT_ID", dotenv.get("FIREBASE_MEASUREMENT_ID", ""));

            // Application URLs
            String appBaseUrl = dotenv.get("APP_BASE_URL", "");
            if (!appBaseUrl.isEmpty()) {
                System.setProperty("APP_BASE_URL", appBaseUrl);
            }

            String apiBaseUrl = dotenv.get("API_BASE_URL", "");
            if (!apiBaseUrl.isEmpty()) {
                System.setProperty("API_BASE_URL", apiBaseUrl);
            }
        }

        SpringApplication.run(BackendApplication.class, args);
    }
}

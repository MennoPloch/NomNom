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
            // MongoDB Configuration - Use direct connection string
            String mongoConnectionString = dotenv.get("MONGODB_CONNECTION_STRING", "");
            if (!mongoConnectionString.isEmpty()) {
                System.setProperty("spring.data.mongodb.uri", mongoConnectionString);
            }

            // Firebase Configuration
            System.setProperty("FIREBASE_PROJECT_ID", dotenv.get("FIREBASE_PROJECT_ID", ""));
            System.setProperty("FIREBASE_PRIVATE_KEY_ID", dotenv.get("FIREBASE_PRIVATE_KEY_ID", ""));
            System.setProperty("FIREBASE_PRIVATE_KEY", dotenv.get("FIREBASE_PRIVATE_KEY", ""));
            System.setProperty("FIREBASE_CLIENT_EMAIL", dotenv.get("FIREBASE_CLIENT_EMAIL", ""));
            System.setProperty("FIREBASE_CLIENT_ID", dotenv.get("FIREBASE_CLIENT_ID", ""));
            System.setProperty("FIREBASE_AUTH_URI", dotenv.get("FIREBASE_AUTH_URI", ""));
            System.setProperty("FIREBASE_TOKEN_URI", dotenv.get("FIREBASE_TOKEN_URI", ""));

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

package com.example.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.Base64;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream serviceAccount;

        String base64Config = System.getenv("FIREBASE_CONFIG_BASE64");

        if (base64Config != null && !base64Config.trim().isEmpty()) {
            try {
                // modified by cursor - better error handling for Firebase config
                byte[] decoded = Base64.getDecoder().decode(base64Config);
                serviceAccount = new ByteArrayInputStream(decoded);
                System.out.println("🔐 Loaded Firebase credentials from environment variable");
            } catch (IllegalArgumentException e) {
                System.err.println("❌ Invalid FIREBASE_CONFIG_BASE64 format: " + e.getMessage());
                throw new IllegalStateException("Invalid FIREBASE_CONFIG_BASE64 format", e);
            }
        } else {
            // modified by cursor - better error handling for missing Firebase config
            System.err.println("❌ FIREBASE_CONFIG_BASE64 environment variable is required");
            System.err.println("Please set FIREBASE_CONFIG_BASE64 in your Render environment variables");
            throw new IllegalStateException("FIREBASE_CONFIG_BASE64 environment variable is required for deployment");
        }

        try {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // modified by cursor - Prevent duplicate FirebaseApp initialization
            FirebaseApp firebaseApp = FirebaseApp.getApps().isEmpty()
                    ? FirebaseApp.initializeApp(options)
                    : FirebaseApp.getInstance();

            System.out.println("✅ Firebase initialized successfully");
            return FirebaseMessaging.getInstance(firebaseApp);
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize Firebase: " + e.getMessage());
            throw new IllegalStateException("Firebase initialization failed", e);
        }
    }
}

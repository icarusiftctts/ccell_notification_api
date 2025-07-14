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

        if (base64Config != null) {
            byte[] decoded = Base64.getDecoder().decode(base64Config);
            serviceAccount = new ByteArrayInputStream(decoded);
            System.out.println("🔐 Loaded Firebase credentials from env");
        } else {
            File file = new File("src/main/resources/service-account.json");
            if (!file.exists()) {
                throw new IllegalStateException("❌ Missing both FIREBASE_CONFIG_BASE64 and local service-account.json");
            }
            serviceAccount = new FileInputStream(file);
            System.out.println("🧪 Loaded Firebase credentials from local file");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        // Prevent duplicate FirebaseApp initialization
        FirebaseApp firebaseApp = FirebaseApp.getApps().isEmpty()
                ? FirebaseApp.initializeApp(options)
                : FirebaseApp.getInstance();

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}

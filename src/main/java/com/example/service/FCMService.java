package com.example.service;

import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FCMService {

    @Autowired
    private FirebaseMessaging firebaseMessaging;

    // Web push configuration constants
    private static final String WEB_ICON_URL = "https://ccellapp.in/icons/icon-192.png";
    private static final String WEB_CLICK_URL = "https://ccellapp.in/notifications";

    public void sendPush(String fcmToken, String title, String messageBody) {
        try {
            // Determine platform from token prefix
            boolean isWebToken = fcmToken.startsWith("fcm");

            Message.Builder messageBuilder = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(messageBody)
                            .build());

            // Platform-specific configurations
            if (isWebToken) {
                messageBuilder.setWebpushConfig(createWebPushConfig(title, messageBody));
            } else {
                messageBuilder.setAndroidConfig(createAndroidConfig());
            }

            String response = firebaseMessaging.send(messageBuilder.build());
            System.out.println("Successfully sent to " +
                    (isWebToken ? "WEB" : "ANDROID") + ": " + response);

        } catch (FirebaseMessagingException e) {
            System.err.println("Failed to send to " + fcmToken);
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("Full error: " + e.getMessage());
        }
    }

    public void sendPushAll(String title, String messageBody) {
        try {
            // Create base message
            Message message = Message.builder()
                    .setTopic("all-users")
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(messageBody)
                            .build())
                    // Send both configs - FCM will use the appropriate one
                    .setAndroidConfig(createAndroidConfig())
                    .setWebpushConfig(createWebPushConfig(title, messageBody))
                    .build();

            String response = firebaseMessaging.send(message);
            System.out.println("Broadcast sent successfully: " + response);
        } catch (FirebaseMessagingException e) {
            System.err.println("Broadcast failed");
            System.err.println("Error: " + e.getErrorCode() + " - " + e.getMessage());
        }
    }

    private AndroidConfig createAndroidConfig() {
        return AndroidConfig.builder()
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setChannelId("c-cell-notifs")
                        .setClickAction("FLUTTER_NOTIFICATION_CLICK")
                        .build())
                .build();
    }

    private WebpushConfig createWebPushConfig(String title, String body) {
        return WebpushConfig.builder()
                .setNotification(new WebpushNotification(
                        title,
                        body,
                        WEB_ICON_URL
                ))
                .setFcmOptions(WebpushFcmOptions.withLink(WEB_CLICK_URL))
                .build();
    }
}
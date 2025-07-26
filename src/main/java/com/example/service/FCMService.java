package com.example.service;

import com.example.model.FCMToken;
import com.example.model.TopicSubscription;
import com.example.repository.FCMTokenRepo;
import com.example.repository.TopicSubscriptionRepository;
import com.google.firebase.messaging.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FCMService {
    @Autowired
    private FCMTokenRepo fcmTokenRepo;
    private TopicSubscriptionRepository topicSubscriptionRepository;

    public void sendPushAll(String title, String body) {
        List<FCMToken> allTokens = fcmTokenRepo.findAll();
        List<String> tokens = allTokens.stream()
                .map(FCMToken::getFcmToken)
                .collect(Collectors.toList());

        if (!tokens.isEmpty()) {
            MulticastMessage message = MulticastMessage.builder()
                    .addAllTokens(tokens)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();
            FirebaseMessaging.getInstance().sendMulticastAsync(message);
        }
    }

    // Updated to handle web topics
    public void sendToTopic(String topic, String title, String body) {
        // 1. Send to mobile devices (native FCM topics)
        Message mobileMessage = Message.builder()
                .setTopic(topic)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();
        FirebaseMessaging.getInstance().sendAsync(mobileMessage);

        // 2. Send to web subscribers (manual topic system)
        List<TopicSubscription> subscriptions = topicSubscriptionRepository.findByIdTopic(topic);
        if (!subscriptions.isEmpty()) {
            List<String> webTokens = subscriptions.stream()
                    .map(sub -> sub.getId().getToken())
                    .collect(Collectors.toList());

            MulticastMessage webMessage = MulticastMessage.builder()
                    .addAllTokens(webTokens)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();
            FirebaseMessaging.getInstance().sendMulticastAsync(webMessage);
        }
    }
}
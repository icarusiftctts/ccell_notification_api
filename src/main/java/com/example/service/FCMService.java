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
    private static final int MAX_FCM_BATCH_SIZE = 500;

    @Autowired
    private FCMTokenRepo fcmTokenRepo;

    @Autowired
    private TopicSubscriptionRepository topicSubscriptionRepository;

    public void sendPushAll(String title, String body) {
        List<String> tokens = fcmTokenRepo.findAll().stream()
                .map(FCMToken::getFcmToken)
                .collect(Collectors.toList());

        sendBatchedPushes(tokens, title, body);
    }

    public void sendToTopic(String topic, String title, String body) {
        // 1. Send to mobile clients using native FCM topic
        Message mobileMessage = Message.builder()
                .setTopic(topic)
                .setNotification(Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build())
                .build();
        FirebaseMessaging.getInstance().sendAsync(mobileMessage);

        // 2. Send to manually tracked web tokens
        List<String> webTokens = topicSubscriptionRepository.findByIdTopic(topic).stream()
                .map(sub -> sub.getId().getToken())
                .collect(Collectors.toList());

        sendBatchedPushes(webTokens, title, body);
    }

    private void sendBatchedPushes(List<String> tokens, String title, String body) {
        for (int i = 0; i < tokens.size(); i += MAX_FCM_BATCH_SIZE) {
            List<String> batch = tokens.subList(i, Math.min(i + MAX_FCM_BATCH_SIZE, tokens.size()));

            MulticastMessage message = MulticastMessage.builder()
                    .addAllTokens(batch)
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build())
                    .build();

            FirebaseMessaging.getInstance().sendMulticastAsync(message);
        }
    }
}

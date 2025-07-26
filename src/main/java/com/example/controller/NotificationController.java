package com.example.controller;

import com.example.dto.TokenRegistrationRequest;
import com.example.dto.TopicSubscriptionRequest;
import com.example.model.Notification;
import com.example.model.TopicSubscription;
import com.example.repository.NotificationRepository;
import com.example.repository.FCMTokenRepo;
import com.example.repository.TopicSubscriptionRepository;
import com.example.service.AuthService;
import com.example.service.FCMService;
import com.example.model.FCMToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final AuthService authService;
    private final NotificationRepository repository;
    private final FCMService fcmService;
    private final FCMTokenRepo tokenRepository;
    private final TopicSubscriptionRepository topicRepo;

    @Autowired
    public NotificationController(
            AuthService authService,
            NotificationRepository repository,
            FCMService fcmService,
            FCMTokenRepo tokenRepository,
            TopicSubscriptionRepository topicRepo
    ) {
        this.authService = authService;
        this.repository = repository;
        this.fcmService = fcmService;
        this.tokenRepository = tokenRepository;
        this.topicRepo = topicRepo;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @GetMapping("/static/icon-192.png")
    public ResponseEntity<Resource> getIcon() throws IOException {
        ClassPathResource iconFile = new ClassPathResource("static/web-app-manifest-192x192.png");
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header("Cache-Control", "public, max-age=31536000, immutable")
                .header("Access-Control-Allow-Origin", "*")
                .body(iconFile);
    }

    @PostMapping
    public ResponseEntity<String> postNotification(
            @RequestBody Notification notification,
            @RequestHeader("X-User-Email") String userEmail
    ) {
        if (!authService.isAuthorized(userEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        notification.setDatePosted(LocalDateTime.now());
        repository.save(notification);

        if (notification.getTargetTopic() != null) {
            fcmService.sendToTopic(notification.getTargetTopic(),
                    notification.getTitle(),
                    notification.getMessage());
        } else {
            fcmService.sendPushAll(notification.getTitle(),
                    notification.getMessage());
        }

        return ResponseEntity.ok("Notification posted successfully");
    }

    @GetMapping("/is-authorized")
    public ResponseEntity<Boolean> isUserAuthorized(
            @RequestHeader("X-User-Email") String userEmail
    ) {
        return ResponseEntity.ok(authService.isAuthorized(userEmail));
    }

    @GetMapping("/approved-senders")
    public ResponseEntity<List<String>> getApprovedSenders() {
        return ResponseEntity.ok(authService.getApprovedSenders());
    }

    @PostMapping("/register-token")
    public ResponseEntity<?> registerToken(
            @RequestBody TokenRegistrationRequest request,
            @RequestHeader(value = "X-User-Email", required = false) String userEmail
    ) {
        String userId = (userEmail != null && authService.isAuthorized(userEmail))
                ? userEmail
                : request.getUserId();

        FCMToken token = tokenRepository.findByFcmToken(request.getToken())
                .orElse(new FCMToken());

        token.setFcmToken(request.getToken());
        token.setUserId(userId);
        token.setGuest(request.isGuest());
        token.setUpdatedAt(LocalDateTime.now());

        if (token.getCreatedAt() == null) {
            token.setCreatedAt(LocalDateTime.now());
        }

        tokenRepository.save(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/subscribe-topic")
    public ResponseEntity<?> subscribeToTopic(
            @RequestBody TopicSubscriptionRequest request
    ) {
        if (!tokenRepository.existsByFcmToken(request.getToken())) {
            return ResponseEntity.badRequest().body("Token not registered");
        }

        if (!topicRepo.existsByTokenAndTopic(request.getToken(), request.getTopic())) {
            topicRepo.save(new TopicSubscription(request.getToken(), request.getTopic()));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unsubscribe-topic")
    public ResponseEntity<?> unsubscribeFromTopic(
            @RequestParam String token,
            @RequestParam String topic
    ) {
        topicRepo.deleteByTokenAndTopic(token, topic);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/merge-user")
    public ResponseEntity<?> mergeGuestToUser(
            @RequestParam String guestUserId,
            @RequestHeader("X-User-Email") String userEmail
    ) {
        if (!authService.isAuthorized(userEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        tokenRepository.updateUserId(guestUserId, userEmail);
        return ResponseEntity.ok().build();
    }
}
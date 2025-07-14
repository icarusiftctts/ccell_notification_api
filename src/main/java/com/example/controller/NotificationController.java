package com.example.controller;

import com.example.model.Notification;
import com.example.repository.NotificationRepository;
import com.example.repository.FCMTokenRepo;
import com.example.service.AuthService;
import com.example.service.FCMService;
import com.example.model.FCMToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final AuthService authService;
    private final NotificationRepository repository;
    private final FCMService fcmService;
    private final FCMTokenRepo tokenRepository;

    @Autowired
    public NotificationController(
            AuthService authService,
            NotificationRepository repository,
            FCMService fcmService,
            FCMTokenRepo tokenRepository
    ) {
        this.authService = authService;
        this.repository = repository;
        this.fcmService = fcmService;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
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

//        Optional<FCMToken> tokenOptional = tokenRepository.findByUserId(userEmail);
//        tokenOptional.ifPresent(token -> {
//            try {
//                fcmService.sendPush(token.getFcmToken(), notification.getTitle(), notification.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
        fcmService.sendPushAll(notification.getTitle(), notification.getMessage());


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
}

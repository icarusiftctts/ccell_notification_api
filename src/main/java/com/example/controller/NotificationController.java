package com.example.controller;

import com.example.model.Notification;
import com.example.repository.NotificationRepository;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;




@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final AuthService authService;
    private final NotificationRepository repository;

    @Autowired
    public NotificationController(AuthService authService, NotificationRepository repository) {
        this.authService = authService;
        this.repository = repository;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<String> postNotification(
            @RequestBody Notification notification,
            @RequestHeader("Authorization") String userEmail
    ) {
        if (!authService.isAuthorized(userEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        notification.setDatePosted(LocalDateTime.now());
        repository.save(notification);
        return ResponseEntity.ok("Notification posted successfully");
    }

    @GetMapping("/is-authorized")
    public ResponseEntity<Boolean> isUserAuthorized(@RequestHeader("Authorization") String userEmail) {
        return ResponseEntity.ok(authService.isAuthorized(userEmail));
    }




}

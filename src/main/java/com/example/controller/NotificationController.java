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
@CrossOrigin(origins = "*") // Later: replace "*" with your frontend URL
public class NotificationController {

    private final AuthService authService;
    private final NotificationRepository repository;

    @Autowired
    public NotificationController(AuthService authService, NotificationRepository repository) {
        this.authService = authService;
        this.repository = repository;
    }

    // GET all notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return repository.findAll();
    }

    // POST a notification (only by authorized users)
    @PostMapping
    public ResponseEntity<String> postNotification(
            @RequestBody Notification notification,
            @RequestHeader("X-User-Email") String userEmail // Custom header (not Authorization)
    ) {
        if (!authService.isAuthorized(userEmail)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        notification.setDatePosted(LocalDateTime.now());
        repository.save(notification);
        return ResponseEntity.ok("Notification posted successfully");
    }

    // Check if the current user is authorized (optional usage in frontend)
    @GetMapping("/is-authorized")
    public ResponseEntity<Boolean> isUserAuthorized(
            @RequestHeader("X-User-Email") String userEmail
    ) {
        return ResponseEntity.ok(authService.isAuthorized(userEmail));
    }

    // Expose approved sender list (used by frontend to show/hide UI)
    @GetMapping("/approved-senders")
    public ResponseEntity<List<String>> getApprovedSenders() {
        return ResponseEntity.ok(authService.getApprovedSenders());
    }
}

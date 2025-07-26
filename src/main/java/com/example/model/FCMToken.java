package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(name = "idx_user_id", columnList = "userId")) // Optimize user lookups
public class FCMToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;        // Firebase UID or guest UUID
    private String fcmToken;      // Firebase Cloud Messaging token
    private boolean isGuest;      // New: Flag for guest users (default false)
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt; // New: Track initial registration time

    // Constructors
    public FCMToken() {}

    // Updated constructor for guest/anonymous users
    public FCMToken(String userId, String fcmToken, boolean isGuest) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.isGuest = isGuest;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    boolean existsByFcmToken(String fcmToken);

    // Getters/Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) {
        this.userId = userId;
        this.updatedAt = LocalDateTime.now(); // Auto-update on user change
    }


    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isGuest() { return isGuest; }  // New
    public void setGuest(boolean guest) {
        isGuest = guest;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }  // New
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
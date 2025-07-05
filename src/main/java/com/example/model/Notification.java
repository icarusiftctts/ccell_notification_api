package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private String sender;
    private String from;
    private String timing;

    private LocalDate eventDate;

    private LocalDateTime datePosted;
    private LocalDateTime createdAt;

    public Notification() {}

    public Notification(Long id, String title, String message, String sender, String from, String timing,
                        LocalDate eventDate, LocalDateTime datePosted, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.sender = sender;
        this.from = from;
        this.timing = timing;
        this.eventDate = eventDate;
        this.datePosted = datePosted;
        this.createdAt = createdAt;
    }

    // Automatically set createdAt and datePosted before insert
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.datePosted == null) {
            this.datePosted = LocalDateTime.now();
        }
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

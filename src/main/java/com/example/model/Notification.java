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
    private LocalDate eventDate;
    private String timing;
    private LocalDateTime datePosted;


    public Notification () {}

    public Notification (String title, String message, String sender, String timing, Long id, LocalDateTime datePosted, LocalDate eventDate) {
        this.title = title;
        this.message = message;
        this.sender = sender;
        this.eventDate = eventDate;
        this.timing = timing;
        this.datePosted = datePosted;
        this.id = id;
    }

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
    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDate getEventDate() {
        return eventDate;
    }
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
    public String getTiming() {
        return timing;
    }
    public void setTiming(String timing) {
        this.timing = timing;
    }
    public LocalDateTime getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

}